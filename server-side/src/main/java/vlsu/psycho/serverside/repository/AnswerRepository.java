package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Answer;

import java.util.UUID;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    boolean existsByExternalId(UUID externalId);
}
