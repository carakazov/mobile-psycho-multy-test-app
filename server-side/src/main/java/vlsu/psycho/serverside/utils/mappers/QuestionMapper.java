package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.QuestionDto;
import vlsu.psycho.serverside.model.Question;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

import java.util.List;

@Mapper(config = MappingConfiguration.class, uses = AnswerMapper.class)
public interface QuestionMapper {
    @Mapping(target = "isMultiAnswer", source = "multiAnswer")
    @Mapping(target = "text", source = "question", qualifiedByName = "getText")
    @Mapping(target = "answers", source = "answers")
    QuestionDto to(Question question);

    @Named("getText")
    default String getTextFrom(Question question) {
        return question.getTexts().get(0).getText();
    }

    List<Question> to(List<Question> questions);
}
