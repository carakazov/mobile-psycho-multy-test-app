package vlsu.psycho.serverside.service;

import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Language;

public interface LanguageService {
    boolean existsByCode(String code);
    Language findByCode(String code);
}
