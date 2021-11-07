package vlsu.psycho.serverside.utils.validation;

import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.validation.dto.AddCustomTestValidationDto;
import vlsu.psycho.serverside.utils.validation.helper.ValidationHelper;

@Component
public class AddCustomTestValidator implements Validator<AddCustomTestValidationDto> {
    @Override
    public void validate(AddCustomTestValidationDto target) {
        ValidationHelper.getInstance().validate(
                target.isLanguageExists(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_LANGUAGE_CODE
        ).throwIfNotValid();
    }
}
