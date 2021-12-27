package vlsu.psycho.serverside.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vlsu.psycho.serverside.config.MappingConfiguration;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.CustomTest;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.Text;
import vlsu.psycho.serverside.utils.language.LanguageHelper;
import vlsu.psycho.serverside.utils.mappers.dto.AddCustomTestMappingDto;

import java.util.Collections;
import java.util.List;

@Mapper(config = MappingConfiguration.class, uses = QuestionMapper.class)
public interface TestMapper {
    @Mapping(target = "description", source = "test", qualifiedByName = "getText")
    @Mapping(target = "questions", source = "questions")
    @Mapping(target = "title", expression = "java(test.getDescriptions().get(0).getTestTitle())")
    @Mapping(target = "expectedTime", expression = "java(test.getDescriptions().get(0).getTestTime())")
    TestDto to(Test test);


    @Mapping(target = "descriptions", source = "addCustomTestDto", qualifiedByName = "setText")
    @Mapping(target = "questions", source = "questions")
    Test mapTest(AddCustomTestDto addCustomTestDto);

    @Mapping(target = "test", source = "mappingDto.dto")
    @Mapping(target = "author", source = "author")
    CustomTest from(AddCustomTestMappingDto mappingDto);


    @Named("setText")
    default List<Text> setText(AddCustomTestDto addCustomTestDto) {
        addCustomTestDto.getQuestions().forEach(question -> question.setLanguage(addCustomTestDto.getLanguage()));
        return Collections.singletonList(new Text()
                .setText(addCustomTestDto.getDescription())
                .setLanguage(addCustomTestDto.getLanguage()));
    }

    @Named("getText")
    default String getTextFrom(Test test) {
        return test.getDescriptions().get(0).getText();
    }
}
