package vlsu.psycho.serverside.utils.validation.dto;

import liquibase.pro.packaged.A;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddCustomTestValidationDto {
    private boolean isLanguageExists;
}
