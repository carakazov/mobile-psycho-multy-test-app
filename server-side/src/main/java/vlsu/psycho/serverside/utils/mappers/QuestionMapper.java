package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.QuestionDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomQuestionDto;
import vlsu.psycho.serverside.model.Question;
import vlsu.psycho.serverside.model.Text;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

import java.util.Collections;
import java.util.List;

@Mapper(config = MappingConfiguration.class, uses = AnswerMapper.class)
public interface QuestionMapper {
    @Mapping(target = "isMultiAnswer", source = "multiAnswer")
    @Mapping(target = "text", source = "question", qualifiedByName = "getText")
    @Mapping(target = "answers", source = "answers")
    QuestionDto to(Question question);


    List<Question> to(List<Question> questions);

    @Mapping(target = "texts", source = "questionDto", qualifiedByName = "setText")
    @Mapping(target = "answers", source = "answers")
    Question from(AddCustomQuestionDto questionDto);

    @Named("getText")
    default String getTextFrom(Question question) {
        return question.getTexts().get(0).getText();
    }

    @Named("setText")
    default List<Text> setTextFrom(AddCustomQuestionDto questionDto) {
        questionDto.getAnswers().forEach(answer -> answer.setLanguage(questionDto.getLanguage()));
        return Collections.singletonList(new Text()
                .setText(questionDto.getText())
                .setLanguage(questionDto.getLanguage()));
    }
}
