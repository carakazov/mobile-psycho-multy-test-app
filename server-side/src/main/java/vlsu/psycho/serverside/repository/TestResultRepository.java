package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.TestResult;

import java.util.List;
import java.util.UUID;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Integer> {
    List<TestResult> findAllByTestExternalId(UUID testExternalId);
}
