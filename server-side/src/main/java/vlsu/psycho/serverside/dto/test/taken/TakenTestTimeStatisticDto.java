package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Accessors(chain = true)
public class TakenTestTimeStatisticDto {
    private String testTitle;
    private Map<LocalDateTime, String> resultsInTime;
}
