package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.BCException;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.taken.TakenTestDetailsDto;
import vlsu.psycho.serverside.dto.test.taken.TakenTestTimeStatisticDto;
import vlsu.psycho.serverside.model.*;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.exception.BusinessException;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.validation.GetTestResultsInTimeValidator;
import vlsu.psycho.serverside.utils.validation.dto.GetTestResultInTimeValidationDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
}
