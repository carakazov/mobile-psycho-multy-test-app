package vlsu.psycho.serverside.service.impl;

import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.taken.AnsweredQuestionDto;
import vlsu.psycho.serverside.model.AnsweredQuestion;
import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.service.TestResultService;

import java.util.List;

@Service
public class TestResultServiceImpl implements TestResultService {
    @Override
    public TestResult calculateResult(List<AnsweredQuestionDto> answeredQuestions) {
        return null;
    }
}
