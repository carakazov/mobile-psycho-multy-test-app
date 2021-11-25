package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.model.Question;

import java.util.UUID;

public interface QuestionService {
    void save(Question question);
    boolean existsByExternalId(UUID externalId);
}
