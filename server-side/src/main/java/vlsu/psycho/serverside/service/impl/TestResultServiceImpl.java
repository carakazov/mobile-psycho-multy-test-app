package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.custom.AddTestResultDto;
import vlsu.psycho.serverside.dto.test.taken.AnsweredQuestionDto;
import vlsu.psycho.serverside.model.AnsweredQuestion;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.repository.TestResultRepository;
import vlsu.psycho.serverside.service.TestResultService;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl implements TestResultService {
    private final TestResultRepository repository;
    private final TextService textService;
    @Override
    public TestResult calculateResult(List<AnsweredQuestionDto> answeredQuestions) {
        return null;
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
}
