package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

@Mapper(config = MappingConfiguration.class, imports = LanguageHelper.class)
public interface TestMapper {
    @Mapping(target = "externalId", source = "test.externalId")
    @Mapping(target = "title", source = "test.title")
    @Mapping(target = "isGenderDepending", source = "test.isGenderDepending")
    @Mapping(target = "isReAnswerEnabled", source = "test.isReAnswerEnabled")
    @Mapping(target = "proceedingType", source = "test.proceedingType")
    @Mapping(target = "expectedTime", source = "test.expectedTime")
    @Mapping(target = "description", expression = "java(LanguageHelper.getTestTextInLanguage(text, languageCode))")
    TestDto to(Test test, String languageCode);
}
