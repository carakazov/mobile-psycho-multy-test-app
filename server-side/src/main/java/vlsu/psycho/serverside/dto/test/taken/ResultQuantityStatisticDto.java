package vlsu.psycho.serverside.dto.test.taken;


import lombok.Data;

import java.util.Map;

@Data
public class ResultQuantityStatisticDto {
    private String testName;
    private Map<String, Long> results;
}
