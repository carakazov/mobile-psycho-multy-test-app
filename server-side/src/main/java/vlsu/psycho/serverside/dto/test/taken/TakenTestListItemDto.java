package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;

import java.util.UUID;

@Data
public class TakenTestListItemDto {
    private String title;
    private UUID externalId;
}
