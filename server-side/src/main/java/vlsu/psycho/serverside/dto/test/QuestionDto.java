package vlsu.psycho.serverside.dto.test;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class QuestionDto {
    private UUID externalId;
    private Boolean isMultiAnswer;
    private byte[] picture;
    private String text;

    private List<AnswerDto> answers;
}
