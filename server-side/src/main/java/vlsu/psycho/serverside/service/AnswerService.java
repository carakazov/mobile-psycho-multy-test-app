package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.model.Answer;

import java.util.UUID;

public interface AnswerService {
    void save(Answer answer);
    boolean existsByExternalId(UUID answerExternalId);
    Answer findByExternalId(UUID externalId);
}
