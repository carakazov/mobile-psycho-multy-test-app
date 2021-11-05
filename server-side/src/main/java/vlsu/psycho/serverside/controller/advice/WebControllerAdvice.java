package vlsu.psycho.serverside.controller.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vlsu.psycho.serverside.config.ApplicationProperties;
import vlsu.psycho.serverside.utils.exception.ValidationException;
import vlsu.psycho.serverside.utils.exception.ValidationExceptionDto;
import vlsu.psycho.serverside.utils.exception.ValidationExceptionResponseDto;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class WebControllerAdvice {
    private final ApplicationProperties applicationProperties;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleValidationException(ValidationException e) {
        logException(e);
        ValidationExceptionResponseDto response = new ValidationExceptionResponseDto();
        e.getCodes().forEach(code -> {
            ApplicationProperties.ErrorDefinition definition = applicationProperties.getErrorMapping().get(code);
            response.addException(
                    new ValidationExceptionDto()
                            .setCode(definition.getCode())
                            .setMessage(definition.getMessage())
            );
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private void logException(Exception e) {
        log.error("exception occurred " + e.getMessage());
    }
}
