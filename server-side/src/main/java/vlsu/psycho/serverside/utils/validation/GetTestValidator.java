package vlsu.psycho.serverside.utils.validation;

import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.validation.dto.GetTestValidationDto;
import vlsu.psycho.serverside.utils.validation.helper.ValidationHelper;

@Component
public class GetTestValidator implements Validator<GetTestValidationDto> {
    @Override
    public void validate(GetTestValidationDto target) {
        ValidationHelper.getInstance().validate(
                target.isTestExist(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_TEST_EXTERNAL_ID
        ).validate(
                target.isLanguageExist(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_LANGUAGE_CODE
        ).throwIfNotValid();
    }
}
