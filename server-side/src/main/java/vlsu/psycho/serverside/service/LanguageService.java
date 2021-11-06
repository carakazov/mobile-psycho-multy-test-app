package vlsu.psycho.serverside.service;

import org.springframework.stereotype.Service;

public interface LanguageService {
    boolean existsByCode(String code);
}
