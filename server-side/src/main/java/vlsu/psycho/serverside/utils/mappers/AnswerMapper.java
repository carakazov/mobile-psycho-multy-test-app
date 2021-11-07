package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.AnswerDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomAnswerDto;
import vlsu.psycho.serverside.model.Answer;
import vlsu.psycho.serverside.model.Text;
import java.util.Collections;
import java.util.List;

@Mapper(config = MappingConfiguration.class)
public interface AnswerMapper {
    @Mapping(target = "text", source = "answer", qualifiedByName = "getText")
    AnswerDto to(Answer answer);

    List<AnswerDto> to(List<Answer> answers);

    @Mapping(target = "texts", source = "answerDto", qualifiedByName = "setText")
    Answer from(AddCustomAnswerDto answerDto);

    @Named("getText")
    default String getTextFrom(Answer answer) {
        return answer.getTexts().get(0).getText();
    }

    @Named("setText")
    default List<Text> setText(AddCustomAnswerDto answerDto) {
        return Collections.singletonList(new Text()
                .setText(answerDto.getText())
                .setLanguage(answerDto.getLanguage()));
    }

}
