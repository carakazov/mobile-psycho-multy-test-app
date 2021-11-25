package vlsu.psycho.serverside.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class ChangePersonalInfoDto {
    private Map<ChangeablePersonalInfo, String> infoToChange;
}
