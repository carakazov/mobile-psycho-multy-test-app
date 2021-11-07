package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Question;
import vlsu.psycho.serverside.repository.QuestionRepository;
import vlsu.psycho.serverside.service.AnswerService;
import vlsu.psycho.serverside.service.QuestionService;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository repository;
    private final AnswerService answerService;
    private final TextService textService;

    @Override
    @Transactional
    public void save(Question question) {
        Question savedQuestion = repository.save(question);
        question.getTexts().forEach(text -> {
            text.setQuestion(question);
            textService.save(text);
        });
        question.getAnswers().forEach(answer -> {
            answer.setQuestion(savedQuestion);
            answerService.save(answer);
        });
    }
}
