package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.test.TestDto;
import vlsu.psycho.serverside.repository.TestRepository;
import vlsu.psycho.serverside.service.LanguageService;
import vlsu.psycho.serverside.service.TestService;
import vlsu.psycho.serverside.utils.mappers.TestMapper;
import vlsu.psycho.serverside.utils.validation.Validator;
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

    @Override
    @Transactional
    public TestDto getTest(UUID testExternalId, String languageCode) {
        getTestValidator.validate(
                new GetTestValidationDto()
                        .setTestExist(repository.existsByExternalId(testExternalId))
                        .setLanguageExist(languageService.existsByCode(languageCode))
        );
        return testMapper.to(repository.findByExternalId(testExternalId), languageCode);
    }
}
