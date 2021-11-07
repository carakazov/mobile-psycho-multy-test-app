package vlsu.psycho.serverside.utils.mappers.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.Language;
import vlsu.psycho.serverside.model.User;

@Data
@Accessors(chain = true)
public class AddCustomTestMappingDto {
    private AddCustomTestDto dto;
    private User author;
}

