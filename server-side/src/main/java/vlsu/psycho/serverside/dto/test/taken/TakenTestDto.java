package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TakenTestDto {
    private UUID testExternalId;
    private LocalDateTime startDate;
    private String languageCode;
    private LocalDateTime finishDate;
    private List<AnsweredQuestionDto> answers;
}
