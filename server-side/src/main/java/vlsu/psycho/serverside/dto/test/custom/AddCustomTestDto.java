package vlsu.psycho.serverside.dto.test.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import vlsu.psycho.serverside.model.Language;
import vlsu.psycho.serverside.model.ProceedingType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Accessors(chain = true)
public class AddCustomTestDto {
    @NotBlank
    @Length(max = 1024)
    private String title;
    @NotBlank
    @Length(max = 1024)
    private String description;
    private Boolean isGenderDepending = Boolean.FALSE;
    private Boolean isReAnswerEnabled = Boolean.FALSE;
    private ProceedingType proceedingType;
    private String expectedTime;
    @NotEmpty
    private List<AddCustomQuestionDto> questions;
    @NotBlank
    @Length(max = 3)
    private String languageCode;

    @JsonIgnore
    private Language language;
}
