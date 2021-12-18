package vlsu.psycho.serverside.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;
import vlsu.psycho.serverside.model.Gender;

@Data
@Accessors(chain = true)
public class PersonalInfoDto {
    private String name;
    private String surname;
    private String middleName;
    private Gender gender;
    private String email;
}
