package vlsu.psycho.serverside.dto.auth;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthResponseDto {
    private String token;
}
