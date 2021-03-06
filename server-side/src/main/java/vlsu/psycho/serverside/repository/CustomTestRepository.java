package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.CustomTest;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomTestRepository extends JpaRepository<CustomTest, Integer> {
    List<CustomTest> findByAuthorExternalId(UUID externalId);
}
