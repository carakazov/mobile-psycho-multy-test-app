package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.CustomTest;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.TestRepository;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.language.LanguageHelper;
import vlsu.psycho.serverside.utils.mappers.TestMapper;
import vlsu.psycho.serverside.utils.mappers.dto.AddCustomTestMappingDto;
import vlsu.psycho.serverside.utils.validation.AddCustomTestValidator;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.AddCustomTestValidationDto;
import vlsu.psycho.serverside.utils.validation.dto.GetTestValidationDto;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository repository;
    private final LanguageService languageService;
    private final Validator<GetTestValidationDto> getTestValidator;
    private final TestMapper testMapper;
    private final QuestionService questionService;
    private final TextService textService;

    @Override
    @Transactional
    public TestDto getTest(UUID testExternalId, String languageCode) {
        getTestValidator.validate(
                new GetTestValidationDto()
                        .setTestExist(repository.existsByExternalId(testExternalId))
                        .setLanguageExist(languageService.existsByCode(languageCode))
        );
        Test test = repository.findByExternalId(testExternalId);
        LanguageHelper.specifyLanguageInTest(test, languageCode);
        return testMapper.to(test);
    }

    @Override
    @Transactional
    public Test save(Test test) {
        Test savedTest = repository.save(test);
        test.getDescriptions().forEach(text -> {
            text.setTest(savedTest);
            textService.save(text);
        });
        test.getQuestions().forEach(question -> {
            question.setTest(savedTest);
            questionService.save(question);
        });
        return savedTest;
    }

    @Override
    public Test getTestByExternalId(UUID testExternalId) {
        return repository.findByExternalId(testExternalId);
    }

    @Override
    public boolean existsByExternalId(UUID testExternalId) {
        return repository.existsByExternalId(testExternalId);
    }
}
