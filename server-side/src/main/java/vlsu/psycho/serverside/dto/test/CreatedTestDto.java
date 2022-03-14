package vlsu.psycho.serverside.dto.test;

import liquibase.pro.packaged.A;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
public class  CreatedTestDto {
    private String testName;
    private UUID externalId;
}
