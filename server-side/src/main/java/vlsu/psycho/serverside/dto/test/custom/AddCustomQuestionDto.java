package vlsu.psycho.serverside.dto.test.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import vlsu.psycho.serverside.model.Language;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Accessors(chain = true)
@Valid
public class AddCustomQuestionDto {
    @NotBlank
    @Length(max = 1024)
    private String text;
    @NotEmpty
    private List<AddCustomAnswerDto> answers;

    @JsonIgnore
    private Language language;
}
