package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Question;

import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    boolean existsByExternalId(UUID externalId);
}
