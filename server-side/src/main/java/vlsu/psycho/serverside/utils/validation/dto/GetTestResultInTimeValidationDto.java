package vlsu.psycho.serverside.utils.validation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetTestResultInTimeValidationDto {
    private boolean testExists;
    private boolean languageExists;
}
