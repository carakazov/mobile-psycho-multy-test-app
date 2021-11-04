package vlsu.psycho.serverside.dto;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
public class RegistrationDto {
    @NotBlank
    @Length(min = 5, max = 15)
    private String login;
    @NotBlank
    @Length(min = 5, max = 15)
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    private String middleName;
    @NotBlank
    private String gender;
    @NotBlank
    @Email
    private String email;
}
