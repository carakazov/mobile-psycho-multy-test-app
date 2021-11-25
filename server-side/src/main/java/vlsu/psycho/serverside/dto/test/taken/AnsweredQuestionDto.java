package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class AnsweredQuestionDto {
    private UUID testQuestionId;
    private UUID testAnswerId;
}
