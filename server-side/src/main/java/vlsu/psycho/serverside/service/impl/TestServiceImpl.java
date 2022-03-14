package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.config.ApplicationProperties;
import vlsu.psycho.serverside.dto.test.ShowTestDto;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.CustomTest;
import vlsu.psycho.serverside.model.Test;
import vlsu.psycho.serverside.model.Text;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.TestRepository;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.language.LanguageHelper;
import vlsu.psycho.serverside.utils.mappers.TestMapper;
import vlsu.psycho.serverside.utils.mappers.dto.AddCustomTestMappingDto;
import vlsu.psycho.serverside.utils.validation.AddCustomTestValidator;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.AddCustomTestValidationDto;
import vlsu.psycho.serverside.utils.validation.dto.GetTestValidationDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final TestRepository repository;
    private final ApplicationProperties applicationProperties;
    private final LanguageService languageService;
    private final Validator<GetTestValidationDto> getTestValidator;
    private final TestMapper testMapper;
    private final QuestionService questionService;
    private final TextService textService;
    private final JwtProvider jwtProvider;
    private final UserService userService;

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
    public Test save(Test test, String title, String expectedTime) {
        Test savedTest = repository.save(test);
        test.getDescriptions().forEach(text -> {
            text.setTest(savedTest);
            text.setTestTime(expectedTime);
            text.setTestTitle(title);
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

    @Override
    public List<ShowTestDto> getAvailableTests(String languageCode) {
        Test test = repository.findByExternalId(applicationProperties.getBurnOutId());
        List<Test> tests = new ArrayList<>();
        tests.add(test);
        List<ShowTestDto> showTests = new ArrayList<>();
        if(jwtProvider.isAuthorized()) {
            UUID clientId = UUID.fromString(jwtProvider.   getClaimFromToken(Claim.EXTERNAL_ID).toString());
            User user = userService.findByExternalId(clientId);
            List<CustomTest> customTests = user.getAllowedTest().stream()
                    .filter(item -> item.getTest().getDescriptions().stream().anyMatch(desc -> desc.getLanguage().getCode().equals(languageCode)))
                    .collect(Collectors.toList());

            customTests.forEach(item -> tests.add(item.getTest()));
        }


        tests.forEach(item -> {
            Text text = item.getDescriptions().stream().filter(desc -> desc.getLanguage().getCode().equals(languageCode)).findFirst().get();
            showTests.add(new ShowTestDto()
                    .setTitle(text.getTestTitle())
                    .setExternalId(item.getExternalId()));
                }
        );
        return showTests;
    }

    @Override
    public TestDto getDefaultTest(String language) {
        return getTest(applicationProperties.getBurnOutId(), language);
    }
}
