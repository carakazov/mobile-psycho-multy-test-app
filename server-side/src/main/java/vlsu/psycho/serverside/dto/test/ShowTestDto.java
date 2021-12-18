package vlsu.psycho.serverside.dto.test;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class ShowTestDto {
    private String title;
    private String expectedTime;
    private UUID externalId;
}
