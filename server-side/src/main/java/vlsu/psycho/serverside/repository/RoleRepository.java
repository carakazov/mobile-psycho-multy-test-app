package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Role;
import vlsu.psycho.serverside.model.RoleTitle;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByTitle(RoleTitle title);
}
