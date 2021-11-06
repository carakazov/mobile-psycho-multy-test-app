package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.objenesis.instantiator.android.AndroidSerializationInstantiator;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.QuestionDto;
import vlsu.psycho.serverside.model.Question;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

@Mapper(config = MappingConfiguration.class, imports = {LanguageHelper.class})
public interface QuestionMapper {
    @Mapping(target = "externalId", source = "question.externalId")
    @Mapping(target = "picture", source = "question.picture")
    @Mapping(target = "isMultiAnswer", source = "question.multiAnswer")
    @Mapping(target = "answers", source = "question.answers")
    @Mapping(target = "text", expression = "java(LanguageHelper.getQuestionTextInLanguage(question, languageCode))")
    QuestionDto to(Question question, String languageCode);
}
