package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByLogin(String login);
    User findByLogin(String login);
    User findByExternalId(UUID externalId);
    List<User> findAllByPsychologist(User psychologist);
}
