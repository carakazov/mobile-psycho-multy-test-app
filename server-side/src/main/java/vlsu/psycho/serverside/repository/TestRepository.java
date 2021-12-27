package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Test;

import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    boolean existsByExternalId(UUID externalId);
    Test findByExternalId(UUID externalId);
}
