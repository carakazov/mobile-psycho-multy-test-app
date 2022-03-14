package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.config.ApplicationProperties;
import vlsu.psycho.serverside.dto.ResultDto;
import vlsu.psycho.serverside.dto.test.CreatedTestDto;
import vlsu.psycho.serverside.dto.test.taken.*;
import vlsu.psycho.serverside.model.*;
import vlsu.psycho.serverside.repository.TakenTestRepository;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.exception.BusinessException;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.TakenTestValidationDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TakenTestServiceImpl implements TakenTestService {
    private final TakenTestRepository repository;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final TestService testService;
    private final Validator<TakenTestValidationDto> takenTestValidator;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final TestResultService testResultService;
    private final ApplicationProperties properties;
    private final CustomTestService customTestService;
    private final TextService textService;

    @Override
    @Transactional
    public ResultDto save(TakenTestDto takenTestDto) {
        takenTestValidator.validate(formValidationDto(takenTestDto));
        TakenTest takenTest = new TakenTest();
        Test test = testService.getTestByExternalId(takenTestDto.getTestExternalId());
        Pair<TestResult, Number> pair = testResultService.calculateResult(takenTestDto.getAnswers(), test.getExternalId(), test.getProceedingType());
        TestResult result = pair.getFirst();
        if(jwtProvider.isAuthorized()) {
            User user = userService.findByExternalId(UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString()));
            takenTest.setUser(user);
            takenTest.setTest(test);
            takenTest.setResult(result);
            takenTest.setStartDate(takenTestDto.getStartDate());
            takenTest.setFinishDate(takenTestDto.getFinishDate());
            repository.save(takenTest);
        }
        return new ResultDto()
                .setText(result.getTexts().stream().filter(
                        item -> item.getLanguage().getCode().equals(takenTestDto.getLanguageCode())
                ).findFirst().orElseThrow(() -> {throw new BusinessException().setCode(ErrorCode.NO_SUCH_TEST_IN_SUCH_LANGUAGE);}).getText())
                .setValue(pair.getSecond());
    }

    @Override
    public boolean existsByExternalId(UUID takenTestExternalId) {
        return repository.existsByExternalId(takenTestExternalId);
    }

    @Override
    public List<TakenTest> getTestsOfClient(UUID clientExternalId) {
        return repository.findAllByUserExternalId(clientExternalId);
    }

    @Override
    public TakenTest getByExternalId(UUID takenTestExternalId) {
        return repository.findByExternalId(takenTestExternalId);
    }

    @Override
    public TakenTestListDto getListOfTakenTests(String languageCode) {
        UUID clientId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        List<TakenTest> takenTests = repository.findAllByUserExternalId(clientId);
        Set<Test> tests = takenTests.stream().map(TakenTest::getTest).collect(Collectors.toSet());
        List<TakenTestListItemDto> items = new ArrayList<>();
        TakenTestListDto response = new TakenTestListDto();
        tests.forEach(item -> {
            if(item.getDescriptions().stream().anyMatch(desc -> desc.getLanguage().getCode().equals(languageCode))) {
                TakenTestListItemDto listItem = new TakenTestListItemDto();
                Text text = item.getDescriptions().stream().filter(desc -> desc.getLanguage().getCode().equals(languageCode)).findFirst().get();
                listItem.setTitle(text.getTestTitle());
                listItem.setExternalId(item.getExternalId());
                items.add(listItem);
            }
        });
        response.setTests(items);
        return response;
    }

    @Override
    public DefaultTestResultDto calculateDefaultTestResult(TakenTestDto takenTestDto) {
        ResultDto dto = save(takenTestDto);
        DefaultTestResultDto result = new DefaultTestResultDto();
        result.setSelfUnsatisfactoryResult(calculateCriteria(properties.getSelfUnsatisfactory(), takenTestDto.getAnswers()));
        result.setLockedInCageResult(calculateCriteria(properties.getLockedInCage(), takenTestDto.getAnswers()));
        result.setProfessionalDutiesReductionResult(calculateCriteria(properties.getProfessionalDutiesReduction(), takenTestDto.getAnswers()));
        result.setEmotionalDetachmentResult(calculateCriteria(properties.getEmotionalDetachment(), takenTestDto.getAnswers()));
        result.setSelfDetachmentResult(calculateCriteria(properties.getSelfDetachment(), takenTestDto.getAnswers()));
        result.setText(dto.getText());
        return result;
    }

    @Override
    public List<CreatedTestDto> getCreated(String languageCode) {
        UUID authorId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        List<CustomTest> tests = customTestService.findByAuthorId(authorId);
        List<CreatedTestDto> result = new ArrayList<>();
        tests.forEach(
                item -> {
                    List<Text> texts = textService.findByTestId(item.getTest().getId());
                    Text text = texts.stream()
                            .filter(tx -> tx.getLanguage().getCode().equals(languageCode))
                            .findFirst().get();
                    result.add(new CreatedTestDto(
                            text.getTestTitle(),
                            item.getTest().getExternalId()
                    ));
                }
        );

        Test burnOutTest = testService.getTestByExternalId(properties.getBurnOutId());
        List<Text> texts = textService.findByTestId(burnOutTest.getId());
        Text text = texts.stream()
                .filter(tx -> tx.getLanguage().getCode().equals(languageCode))
                .findFirst().get();
        result.add(
                new CreatedTestDto(
                        text.getTestTitle(),
                        burnOutTest.getExternalId()
                )
        );
        return result;
    }

    @Override
    public List<CreatedTestDto> getMyTakenTests(String languageCode) {
        List<CreatedTestDto> result = new ArrayList<>();
        UUID authorId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User user = userService.findByExternalId(authorId);
        user.getTakenTests().forEach(
                item -> {
                    result.add(new CreatedTestDto(
                            textService.findByTestId(item.getTest().getId()).stream().filter(text -> text.getLanguage().getCode().equals(languageCode)).findFirst().get().getTestTitle(),
                            item.getExternalId()
                    ));
                }
        );
        return result;
    }

    private Integer calculateCriteria(List<UUID> criteriaIds, List<AnsweredQuestionDto> answeredQuestions) {
        return criteriaIds.stream().flatMapToInt(
                item -> {
                    AnsweredQuestionDto aq = answeredQuestions.stream()
                            .filter(ans -> ans.getTestQuestionId().equals(item))
                            .findFirst().get();
                    Answer answer = answerService.findByExternalId(aq.getTestAnswerId());
                    return IntStream.of(answer.getValue().intValue());
                }
        ).sum();
    }

    private TakenTestValidationDto formValidationDto(TakenTestDto takenTestDto) {
        TakenTestValidationDto validationDto = new TakenTestValidationDto();
        validationDto.setEndDate(takenTestDto.getFinishDate());
        validationDto.setStartDate(takenTestDto.getStartDate());
        validationDto.setTestExists(testService.existsByExternalId(takenTestDto.getTestExternalId()));
        validationDto.setAnswersExist(
                takenTestDto.getAnswers().stream().map(answeredQuestion -> answerService.existsByExternalId(answeredQuestion.getTestAnswerId()))
                        .collect(Collectors.toList())
        );
        validationDto.setQuestionsExist(
                takenTestDto.getAnswers().stream().map(answeredQuestion -> questionService.existsByExternalId(answeredQuestion.getTestQuestionId()))
                        .collect(Collectors.toList())
        );
        return validationDto;
    }
}
