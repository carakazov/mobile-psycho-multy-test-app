package vlsu.psycho.serverside.utils.exception;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationExceptionResponseDto {
    private final List<ExceptionDto> exceptions = new ArrayList<>();

    public void addException(ExceptionDto e) {
        exceptions.add(e);
    }
}
