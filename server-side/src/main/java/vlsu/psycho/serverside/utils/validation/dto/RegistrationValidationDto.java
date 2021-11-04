package vlsu.psycho.serverside.utils.validation.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationValidationDto {
    private boolean isLoginExists;
    private String login;
    private String password;
}
