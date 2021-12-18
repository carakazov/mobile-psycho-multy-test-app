package vlsu.psycho.serverside.utils.validation;

import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.validation.dto.GetTestResultInTimeValidationDto;
import vlsu.psycho.serverside.utils.validation.helper.ValidationHelper;

@Component
public class GetTestResultsInTimeValidator implements Validator<GetTestResultInTimeValidationDto> {
    @Override
    public void validate(GetTestResultInTimeValidationDto target) {
        ValidationHelper.getInstance().validate(
                target.isTestExists(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_TEST_EXTERNAL_ID
        ).validate(
                target.isLanguageExists(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_LANGUAGE_CODE
        ).throwIfNotValid();
    }
}
