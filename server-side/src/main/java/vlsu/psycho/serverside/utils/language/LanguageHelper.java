package vlsu.psycho.serverside.utils.language;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import vlsu.psycho.serverside.model.Answer;
import vlsu.psycho.serverside.model.Question;
import vlsu.psycho.serverside.model.Test;

import java.util.List;


@UtilityClass
public class LanguageHelper {
    public static String getAnswerTextInLanguage(Answer answer, String languageCode) {
        return answer.getTexts().stream().filter(
                text -> text.getLanguage().getCode().equals(languageCode)
        ).findFirst().get().getText();
    }

    public static String getQuestionTextInLanguage(Question question, String languageCode) {
        return question.getTexts().stream().filter(
                text -> text.getLanguage().getCode().equals(languageCode)
        ).findFirst().get().getText();
    }

    public static String getTestTextInLanguage(Test test, String languageCode) {
        return test.getDescriptions().stream().filter(
                text -> text.getLanguage().getCode().equals(languageCode)
        ).findFirst().get().getText();
    }
}
