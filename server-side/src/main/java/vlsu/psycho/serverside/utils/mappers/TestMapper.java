package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.utils.language.LanguageHelper;

@Mapper(config = MappingConfiguration.class, uses = QuestionMapper.class)
public interface TestMapper {
    @Mapping(target = "description", source = "test", qualifiedByName = "getText")
    @Mapping(target = "questions", source = "questions")
    TestDto to(Test test);

    @Named("getText")
    default String getTextFrom(Test test) {
        return test.getDescriptions().get(0).getText();
    }
}
