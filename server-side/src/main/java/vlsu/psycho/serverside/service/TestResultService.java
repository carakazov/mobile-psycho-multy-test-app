package vlsu.psycho.serverside.service;

import org.springframework.data.util.Pair;
import vlsu.psycho.serverside.dto.test.custom.AddTestResultDto;
import vlsu.psycho.serverside.dto.test.taken.AnsweredQuestionDto;
import vlsu.psycho.serverside.model.ProceedingType;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.TestResult;

import java.util.List;
import java.util.UUID;

public interface TestResultService {
    Pair<TestResult, Number> calculateResult(List<AnsweredQuestionDto> answeredQuestions, UUID testExternalId, ProceedingType proceedingType);
    void saveTestResults(List<AddTestResultDto> results, Test test, String language);
}
