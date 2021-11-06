package vlsu.psycho.serverside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vlsu.psycho.serverside.model.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {
    boolean existsByCode(String code);
}
