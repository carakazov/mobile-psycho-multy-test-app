package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TakenTestDetailsDto {
    private String description;
    private String title;

    private Map<String, List<String>> questionAnswers;
    private String result;
}
