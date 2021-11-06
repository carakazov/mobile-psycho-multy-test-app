package vlsu.psycho.serverside.dto.test;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class AnswerDto {
    private UUID externalId;
    private Double value;
    private byte[] picture;
    private String text;
}
