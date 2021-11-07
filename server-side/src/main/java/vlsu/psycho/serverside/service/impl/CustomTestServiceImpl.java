package vlsu.psycho.serverside.service.impl;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import vlsu.psycho.serverside.dto.test.custom.AddCustomTestDto;
import vlsu.psycho.serverside.model.CustomTest;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.CustomTestRepository;
import vlsu.psycho.serverside.service.*;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.mappers.TestMapper;
import vlsu.psycho.serverside.utils.mappers.dto.AddCustomTestMappingDto;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.AddCustomTestValidationDto;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@Service
public class CustomTestServiceImpl implements CustomTestService {
    private final CustomTestRepository repository;
    private final Validator<AddCustomTestValidationDto> addCustomTestValidator;
    private final TestMapper testMapper;
    private final UserService userService;
    private final LanguageService languageService;
    private final JwtProvider jwtProvider;
    private final TestService testService;

    @Override
    @Transactional
    public void createCustomTest(AddCustomTestDto addCustomTestDto) {
        addCustomTestValidator.validate(new AddCustomTestValidationDto().setLanguageExists(
                languageService.existsByCode(addCustomTestDto.getLanguageCode())
        ));
        addCustomTestDto.setLanguage(languageService.findByCode(addCustomTestDto.getLanguageCode()));
        UUID externalId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User author = userService.findByExternalId(externalId);
        CustomTest customTest = testMapper.from(new AddCustomTestMappingDto().setDto(addCustomTestDto).setAuthor(author));
        customTest.setAllowedUsers(userService.getClientsOfUser());
        testService.save(customTest.getTest());
        repository.save(customTest);
    }
}
