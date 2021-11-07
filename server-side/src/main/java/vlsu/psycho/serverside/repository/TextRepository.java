package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Text;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {
}
