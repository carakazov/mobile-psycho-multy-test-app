package vlsu.psycho.serverside.utils.validation;

import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.dto.test.custom.AddTestResultDto;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.validation.dto.AddCustomTestValidationDto;
import vlsu.psycho.serverside.utils.validation.helper.ValidationHelper;

import java.util.List;
import java.util.function.Predicate;

@Component
public class AddCustomTestValidator implements Validator<AddCustomTestValidationDto> {
    @Override
    public void validate(AddCustomTestValidationDto target) {
        ValidationHelper.getInstance().validate(
                target.isLanguageExists(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_LANGUAGE_CODE
        ).validate(
                target.getResults(),
                list -> list.stream().noneMatch(
                        item -> item.getMaxBorder().equals(item.getMinBorder())
                ),
                ErrorCode.SAME_RESULT_BORDERS
        ).throwIfNotValid();
    }
}
