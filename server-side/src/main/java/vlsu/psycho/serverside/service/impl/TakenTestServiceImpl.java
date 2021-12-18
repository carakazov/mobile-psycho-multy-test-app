package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.taken.TakenTestDto;
import vlsu.psycho.serverside.model.TakenTest;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.TakenTestRepository;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.exception.BusinessException;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.TakenTestValidationDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Override
    @Transactional
    public String save(TakenTestDto takenTestDto) {
        takenTestValidator.validate(formValidationDto(takenTestDto));

        User user = userService.findByExternalId(UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString()));
        TakenTest takenTest = new TakenTest();
        Test test = testService.getTestByExternalId(takenTestDto.getTestExternalId());
        takenTest.setTest(test);
        takenTest.setUser(user);
        TestResult result = testResultService.calculateResult(takenTestDto.getAnswers(), test.getExternalId(), test.getProceedingType());
        takenTest.setResult(result);
        takenTest.setStartDate(takenTestDto.getStartDate());
        takenTest.setFinishDate(takenTestDto.getFinishDate());
        repository.save(takenTest);
        return result.getTexts().stream().filter(
                item -> item.getLanguage().getCode().equals(takenTestDto.getLanguageCode())
        ).findFirst().orElseThrow(() -> {throw new BusinessException().setCode(ErrorCode.NO_SUCH_TEST_IN_SUCH_LANGUAGE);}).getText();
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
