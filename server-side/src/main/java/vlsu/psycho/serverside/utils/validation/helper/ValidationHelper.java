package vlsu.psycho.serverside.utils.validation.helper;

import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.exception.ValidationException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ValidationHelper {
    private final Set<ErrorCode> codes = new HashSet<>();

    public static ValidationHelper getInstance() {
        return new ValidationHelper();
    }

    public <T> ValidationHelper validate(T target, Predicate<T> predicate, ErrorCode code) {
        if(!predicate.test(target)) {
            codes.add(code);
        }
        return this;
    }

    public void throwIfNotValid() {
        if(!codes.isEmpty()) {
            ValidationException exception = new ValidationException();
            codes.forEach(exception::addCode);
            throw exception;
        }
    }

    public void addCode(ErrorCode code) {
        codes.add(code);
    }
}
