package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
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
    public Pair<TestResult, Number> calculateResult(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId, ProceedingType proceedingType) {
        Pair<TestResult, Number> result = null;
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

    private Pair<TestResult, Number> calculateBurnOut(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        int sum;
        List<UUID> answers  = answeredQuestions.stream()
                .map(AnsweredQuestionDto::getTestAnswerId)
                .collect(Collectors.toList());
        sum = answers.stream().mapToDouble(item -> answerService.findByExternalId(item).getValue()).mapToInt(value -> (int) value).sum();
        List<TestResult> resultList = repository.findAllByTestExternalId(testExternalId);
        return Pair.of(resultList.stream()
                .filter(item -> item.getMinBorder() <= sum && sum <= item.getMaxBorder())
                .findFirst().get(), sum);
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

    private Pair<TestResult, Number> calculateArithmeticAverage(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        double average = answeredQuestions.stream()
                .map(AnsweredQuestionDto::getTestAnswerId)
                .map(answerService::findByExternalId)
                .mapToDouble(answer -> answer.getValue().intValue())
                .average().getAsDouble();
        return Pair.of(repository.findAllByTestExternalId(testExternalId).stream()
                .filter(item -> item.getMinBorder() >= average && average <= item.getMaxBorder())
                .findFirst().get(), average);
    }

    private Pair<TestResult, Number> calculateGeometricAverage(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId) {
        double multiply = 1;
        multiply *= answeredQuestions.stream().map(item -> answerService.findByExternalId(item.getTestAnswerId())).mapToDouble(Answer::getValue).reduce(1, (a, b) -> a * b);
        double result = Math.pow(multiply, 1.0/answeredQuestions.size());
        return Pair.of(repository.findAllByTestExternalId(testExternalId).stream()
                .filter(item -> item.getMinBorder() >= result && result <= item.getMaxBorder())
                .findFirst().get(), multiply);
    }
}
