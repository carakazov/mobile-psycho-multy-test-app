package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.TakenTest;

@Repository
public interface TakenTestRepository extends JpaRepository<TakenTest, Integer> {
}
