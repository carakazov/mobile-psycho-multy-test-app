package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Language;
import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.model.Text;
import vlsu.psycho.serverside.repository.TextRepository;
import vlsu.psycho.serverside.service.LanguageService;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {
    private final TextRepository repository;
    private final LanguageService languageService;

    @Override
    @Transactional
    public void save(Text text) {
        repository.save(text);
    }

    @Override
    public void saveForResult(String text, String languageCode, TestResult result) {
        Language language = languageService.findByCode(languageCode);
        Text textObj = new Text();
        textObj.setText(text);
        textObj.setResult(result);
        textObj.setLanguage(language);
        save(textObj);
    }

    @Override
    public List<Text> findByTestId(Integer testExternalId) {
        return repository.findByTestId(testExternalId);
    }
}
