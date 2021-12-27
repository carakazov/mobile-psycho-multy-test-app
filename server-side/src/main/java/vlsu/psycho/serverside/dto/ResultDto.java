package vlsu.psycho.serverside.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResultDto {
    private String text;
    private Number value;
}
