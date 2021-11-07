package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.AnswerDto;
import vlsu.psycho.serverside.model.Answer;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MappingConfiguration.class)
public interface AnswerMapper {
    @Mapping(target = "text", source = "answer", qualifiedByName = "getText")
    AnswerDto to(Answer answer);

    @Named("getText")
    default String getTextFrom(Answer answer) {
        return answer.getTexts().get(0).getText();
    }

    List<AnswerDto> to(List<Answer> answers);
}
