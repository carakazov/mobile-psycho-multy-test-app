package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.TakenTest;
import vlsu.psycho.serverside.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface TakenTestRepository extends JpaRepository<TakenTest, Integer> {
    List<TakenTest> findAllByUserExternalId(UUID externalId);
    boolean existsByExternalId(UUID externalId);
    TakenTest findByExternalId(UUID externalId);
}
