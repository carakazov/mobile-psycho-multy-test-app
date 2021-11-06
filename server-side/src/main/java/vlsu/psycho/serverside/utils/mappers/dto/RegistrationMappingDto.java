package vlsu.psycho.serverside.utils.mappers.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import vlsu.psycho.serverside.dto.user.RegistrationDto;
import vlsu.psycho.serverside.model.Role;

@Data
@Accessors(chain = true)
public class RegistrationMappingDto {
    private RegistrationDto dto;
    private Role role;
}
