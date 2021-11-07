package vlsu.psycho.serverside.dto.test.custom;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import vlsu.psycho.serverside.model.Language;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Valid
public class AddCustomAnswerDto {
    @NotBlank
    @Length(max = 1024)
    private String text;
    @NotNull
    private Double value;

    @JsonIgnore
    private Language language;
}
