package vlsu.psycho.serverside.utils.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationExceptionResponseDto {
    private final List<ValidationExceptionDto> exceptions = new ArrayList<>();

    public void addException(ValidationExceptionDto e) {
        exceptions.add(e);
    }
}
