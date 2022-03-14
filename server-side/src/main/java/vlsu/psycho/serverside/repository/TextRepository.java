package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Text;

import java.util.List;

@Repository
public interface TextRepository extends JpaRepository<Text, Integer> {
    List<Text> findByTestId(Integer id);
}
