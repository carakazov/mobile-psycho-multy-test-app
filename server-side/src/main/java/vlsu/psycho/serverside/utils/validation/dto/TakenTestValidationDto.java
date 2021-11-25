package vlsu.psycho.serverside.utils.validation.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TakenTestValidationDto {
    private boolean isTestExists;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    private List<Boolean> questionsExist;
    private List<Boolean> answersExist;
}
