package vlsu.psycho.serverside.utils.exception;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class ValidationException extends RuntimeException {
    private final Set<ErrorCode> codes = new HashSet<>();

    public void addCode(ErrorCode code) {
        codes.add(code);
    }
}
