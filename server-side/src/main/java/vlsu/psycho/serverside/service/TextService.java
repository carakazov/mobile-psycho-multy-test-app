package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.model.Text;

public interface TextService {
    void save(Text text);
    void saveForResult(String text, String languageCode, TestResult result);
}
