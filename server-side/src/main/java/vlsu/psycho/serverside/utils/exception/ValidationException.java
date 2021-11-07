package vlsu.psycho.serverside.utils.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValidationException extends RuntimeException {
    private final Set<ErrorCode> codes = new HashSet<>();

    public void addCode(ErrorCode code) {
        codes.add(code);
    }
}
