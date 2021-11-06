package vlsu.psycho.serverside.dto.test;

import lombok.Data;
import lombok.experimental.Accessors;
import vlsu.psycho.serverside.model.ProceedingType;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class TestDto {
    private UUID externalId;
    private String title;
    private Boolean isGenderDepending;
    private Boolean isReAnswerEnabled;
    private ProceedingType proceedingType;
    private String description;
    private String expectedTime;

    private List<QuestionDto> questions;
}
