package vlsu.psycho.serverside.service;

import org.springframework.data.relational.core.sql.In;
import vlsu.psycho.serverside.model.TestResult;
import vlsu.psycho.serverside.model.Text;

import java.util.List;
import java.util.UUID;

public interface TextService {
    void save(Text text);
    void saveForResult(String text, String languageCode, TestResult result);
    List<Text> findByTestId(Integer id);
}
