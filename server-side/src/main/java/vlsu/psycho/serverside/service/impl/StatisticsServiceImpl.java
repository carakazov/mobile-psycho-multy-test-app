package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.taken.ResultQuantityStatisticDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestTimeStatisticDto;
import vlsu.psycho.serverside.model.*;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.exception.BusinessException;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.validation.GetTestResultsInTimeValidator;
import vlsu.psycho.serverside.utils.validation.dto.GetTestResultInTimeValidationDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final JwtProvider jwtProvider;
    private final TakenTestService takenTestService;
    private final LanguageService languageService;
    private final TestService testService;
    private final GetTestResultsInTimeValidator getTestResultsInTimeValidator;
    private final UserService userService;
    private final TextService textService;

    @Override
    public TakenTestTimeStatisticDto getResultsInTime(UUID testExternalId, String languageCode) {
        getTestResultsInTimeValidator.validate(
                new GetTestResultInTimeValidationDto()
                        .setLanguageExists(languageService.existsByCode(languageCode))
                        .setTestExists(testService.existsByExternalId(testExternalId))
        );
        UUID clientGuid = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        List<TakenTest> clientTests = takenTestService.getTestsOfClient(clientGuid);
        Map<LocalDateTime, String> testResultsInTime = new HashMap<>();
        clientTests.forEach(
                item -> {
                    String result = item.getResult().getTexts().stream()
                            .filter(text -> text.getLanguage().getCode().equals(languageCode))
                            .findFirst().orElseThrow(() -> {throw new BusinessException().setCode(ErrorCode.NO_SUCH_TEST_IN_SUCH_LANGUAGE);}).getText();
                    testResultsInTime.put(item.getFinishDate(), result);
                }
        );
        return new TakenTestTimeStatisticDto()
                .setResultsInTime(testResultsInTime)
                .setTestTitle(testService.getTest(testExternalId, languageCode).getTitle());
    }

    @Override
    public TakenTestTimeStatisticDto getResultOfClient(UUID testExternalId, String languageCode, UUID clientExternalId) {
        getTestResultsInTimeValidator.validate(
                new GetTestResultInTimeValidationDto()
                        .setLanguageExists(languageService.existsByCode(languageCode))
                        .setTestExists(testService.existsByExternalId(testExternalId))
        );
        UUID psychoId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User psycho  = userService.findByExternalId(psychoId);
        if(psycho.getClients().stream().map(User::getExternalId).noneMatch(item -> item.equals(clientExternalId))) {
            throw new BusinessException().setCode(ErrorCode.NOT_YOUR_CLIENT);
        }
        List<TakenTest> clientTests = takenTestService.getTestsOfClient(clientExternalId);
        Map<LocalDateTime, String> testResultsInTime = new HashMap<>();
        clientTests.forEach(
                item -> {
                    String result = item.getResult().getTexts().stream()
                            .filter(text -> text.getLanguage().getCode().equals(languageCode))
                            .findFirst().orElseThrow(() -> {throw new BusinessException().setCode(ErrorCode.NO_SUCH_TEST_IN_SUCH_LANGUAGE);}).getText();
                    testResultsInTime.put(item.getFinishDate(), result);
                }
        );
        return new TakenTestTimeStatisticDto()
                .setResultsInTime(testResultsInTime)
                .setTestTitle(testService.getTest(testExternalId, languageCode).getTitle());
    }

    @Override
    public ResultQuantityStatisticDto getResultQuantity(UUID testExternalId, String languageCode) {
        Test test = testService.getTestByExternalId(testExternalId);
        UUID psychoId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User psycho  = userService.findByExternalId(psychoId);
        ResultQuantityStatisticDto result = new ResultQuantityStatisticDto();
        Map<String, Long> map = new HashMap<>();
        Text text = textService.findByTestId(test.getId()).stream().filter(item -> item.getLanguage().getCode().equals(languageCode)).findFirst().get();
        result.setTestName(text.getTestTitle());
        test.getResults().forEach(
                item -> {
                    AtomicLong counter = new AtomicLong();
                    psycho.getClients().forEach(
                        client -> {
                            List<TestResult> results = client.getTakenTests().stream().map(TakenTest::getResult).collect(Collectors.toList());
                            counter.set(results.stream().filter(res -> res.equals(item)).count());
                        }
                    );
                    map.put(item.getTexts().stream().filter(txt -> txt.getLanguage().getCode().equals(languageCode)).findFirst().get().getText(), counter.get());
                }
        );
        result.setResults(map);
        return result;
    }

    @Override
    public ResultQuantityStatisticDto getMyResults(String languageCode, UUID testExternalId) {
        ResultQuantityStatisticDto dto = new ResultQuantityStatisticDto();
        Map<String, Long> map = new HashMap<>();
        UUID psychoId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User user  = userService.findByExternalId(psychoId);
        Test test = testService.getTestByExternalId(testExternalId);
        List<TestResult> results = user.getTakenTests().stream()
                .filter(item -> item.getTest().getExternalId().equals(testExternalId))
                .map(TakenTest::getResult).collect(Collectors.toList());

        test.getResults().forEach(
                item -> {
                    map.put(
                            item.getTexts().stream().filter(txt -> txt.getLanguage().getCode().equals(languageCode)).findFirst().get().getText(),
                            results.stream().filter(res -> res.equals(item)).count()
                    );
                }
        );

        dto.setTestName(textService.findByTestId(test.getId()).stream().filter(item -> item.getLanguage().getCode().equals(languageCode)).findFirst().get().getTestTitle());
        dto.setResults(map);
        return dto;
    }
}
