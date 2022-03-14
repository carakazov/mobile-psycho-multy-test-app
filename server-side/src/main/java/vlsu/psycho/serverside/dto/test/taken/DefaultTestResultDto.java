package vlsu.psycho.serverside.dto.test.taken;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DefaultTestResultDto {
    private Integer selfUnsatisfactoryResult;
    private Integer lockedInCageResult;
    private Integer professionalDutiesReductionResult;
    private Integer emotionalDetachmentResult;
    private Integer selfDetachmentResult;
    private String text;
}
