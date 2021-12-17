package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.config.ApplicationProperties;
import vlsu.psycho.serverside.dto.test.custom.AddTestResultDto;
import vlsu.psycho.serverside.dto.test.taken.AnsweredQuestionDto;
import vlsu.psycho.serverside.model.*;
import vlsu.psycho.serverside.repository.TestResultRepository;
import vlsu.psycho.serverside.service.AnswerService;
import vlsu.psycho.serverside.service.TestResultService;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl implements TestResultService {
    private final TestResultRepository repository;
    private final TextService textService;
    private final AnswerService answerService;
    private final ApplicationProperties applicationProperties;
    @Override
    public TestResult calculateResult(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId, ProceedingType proceedingType) {
        TestResult result = null;
        switch (proceedingType) {
            case AVERAGE:
                result = calculateArithmeticAverage(answeredQuestions, testExternalId);
                break;
            case GEOMETRIC:
                result = calculateGeometricAverage(answeredQuestions, testExternalId);
                break;
            default:
                result = calculateBurnOut(answeredQuestions, testExternalId);
                break;
        }
        return result;
    }

    @Override
    @Transactional
    public void saveTestResults(List<AddTestResultDto> results, Test test, String language) {
        results.forEach(
                item -> {
                    TestResult result = new TestResult();
                    result.setTest(test);
                    result.setMaxBorder(item.getMaxBorder());
                    result.setMinBorder(item.getMinBorder());
                    result = repository.save(result);
                    textService.saveForResult(item.getText(), language, result);
                }
        );
    }

    private TestResult calculateBurnOut(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        Map<UUID, UUID> questionAnswerMap = formMapFromListForBurnOut(answeredQuestions);
        int resultNumber = calculateSymptom(questionAnswerMap, applicationProperties.getSelfUnsatisfactory());
        resultNumber += calculateSymptom(questionAnswerMap, applicationProperties.getLockedInCage());
        resultNumber += calculateSymptom(questionAnswerMap, applicationProperties.getProfessionalDutiesReduction());
        resultNumber += calculateSymptom(questionAnswerMap, applicationProperties.getEmotionalDetachment());
        resultNumber += calculateSymptom(questionAnswerMap, applicationProperties.getSelfDetachement());
        List<TestResult> resultList = repository.findAllByTestExternalId(testExternalId);
        int finalResultNumber = resultNumber;
        return resultList.stream()
                .filter(item -> item.getMinBorder() >= finalResultNumber && finalResultNumber <= item.getMaxBorder())
                .findFirst().get();
    }

    private int calculateSymptom(Map<UUID, UUID> questionAnswerMap, List<UUID> symptom) {
        return  symptom.stream()
                .map(questionAnswerMap::get)
                .map(answerService::findByExternalId)
                .mapToInt(answer -> answer.getValue().intValue())
                .sum();
    }

    private Map<UUID, UUID> formMapFromListForBurnOut(List<AnsweredQuestionDto> dtoList) {
        Map<UUID, UUID> map = new HashMap<>();
        dtoList.forEach(
                item -> map.put(item.getTestQuestionId(), item.getTestAnswerId())
        );
        return map;
    }

    private TestResult calculateArithmeticAverage(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        double average = answeredQuestions.stream()
                .map(AnsweredQuestionDto::getTestAnswerId)
                .map(answerService::findByExternalId)
                .mapToDouble(answer -> answer.getValue().intValue())
                .average().getAsDouble();
        return repository.findAllByTestExternalId(testExternalId).stream()
                .filter(item -> item.getMinBorder() >= average && average <= item.getMaxBorder())
                .findFirst().get();
    }

    private TestResult calculateGeometricAverage(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        double multiply = 0;
        multiply *= answeredQuestions.stream().map(item -> answerService.findByExternalId(item.getTestAnswerId())).mapToDouble(Answer::getValue).reduce(1, (a, b) -> a * b);
        double result = Math.pow(multiply, 1.0/answeredQuestions.size());
        return repository.findAllByTestExternalId(testExternalId).stream()
                .filter(item -> item.getMinBorder() >= result && result <= item.getMaxBorder())
                .findFirst().get();
    }
}
