package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.test.custom.AddTestResultDto;
import vlsu.psycho.serverside.dto.test.taken.AnsweredQuestionDto;
import vlsu.psycho.serverside.model.AnsweredQuestion;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.TestResult;

import java.util.List;

public interface TestResultService {
    TestResult calculateResult(List<AnsweredQuestionDto> answeredQuestions);
    void saveTestResults(List<AddTestResultDto> results, Test test, String language);
}
