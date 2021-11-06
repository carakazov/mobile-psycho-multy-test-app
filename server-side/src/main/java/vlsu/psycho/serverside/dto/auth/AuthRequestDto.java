package vlsu.psycho.serverside.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class AuthRequestDto {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
