package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.AnswerDto;
import vlsu.psycho.serverside.model.Answer;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

import java.util.ArrayList;
import java.util.List;

@Mapper(config = MappingConfiguration.class, imports = { LanguageHelper.class })
public interface AnswerMapper {
    @Mapping(target = "externalId", source = "answer.externalId")
    @Mapping(target = "value", source = "answer.value")
    @Mapping(target = "picture", source = "answer.picture")
    @Mapping(target = "text", expression = "java(LanguageHelper.getAnswerTextInLanguage(answer, languageCode))")
    AnswerDto to(Answer answer, String languageCode);

}
