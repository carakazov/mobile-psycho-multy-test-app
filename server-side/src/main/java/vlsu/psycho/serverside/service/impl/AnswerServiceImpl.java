package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Answer;
import vlsu.psycho.serverside.repository.AnswerRepository;
import vlsu.psycho.serverside.service.AnswerService;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository repository;
    private final TextService textService;

    @Override
    @Transactional
    public void save(Answer answer) {
        Answer savedAnswer = repository.save(answer);
        answer.getTexts().forEach(text -> {
            text.setAnswer(savedAnswer);
            textService.save(text);
        });
    }

    @Override
    public boolean existsByExternalId(UUID answerExternalId) {
        return repository.existsByExternalId(answerExternalId);
    }
}
