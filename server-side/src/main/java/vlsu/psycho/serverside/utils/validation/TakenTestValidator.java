package vlsu.psycho.serverside.utils.validation;

import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.validation.dto.TakenTestValidationDto;
import vlsu.psycho.serverside.utils.validation.helper.ValidationHelper;

@Component
public class TakenTestValidator implements Validator<TakenTestValidationDto> {
    @Override
    public void validate(TakenTestValidationDto target) {
        ValidationHelper helper = ValidationHelper.getInstance();
        helper.validate(
                target.isTestExists(),
                Boolean.TRUE::equals,
                ErrorCode.UNEXPECTED_TEST_EXTERNAL_ID
        ).validate(
                target.getQuestionsExist(),
                questions -> questions.stream().noneMatch(Boolean.FALSE::equals),
                ErrorCode.UNEXPECTED_QUESTION_EXTERNAL_ID
        ).validate(
                target.getAnswersExist(),
                answer -> answer.stream().noneMatch(Boolean.FALSE::equals),
                ErrorCode.UNEXPECTED_ANSWER_EXTERNAL_ID
        ).validate(
                target,
                dto -> dto.getEndDate().isAfter(dto.getStartDate()),
                ErrorCode.END_DATE_IS_BEFORE_START_DATE
        ).throwIfNotValid();
    }
}
