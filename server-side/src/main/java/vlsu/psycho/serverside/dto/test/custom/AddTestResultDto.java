package vlsu.psycho.serverside.dto.test.custom;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Valid
public class AddTestResultDto {
    @NotNull
    private String text;
    @NotNull
    private Double minBorder;
    @NotNull
    private Double maxBorder;
}
