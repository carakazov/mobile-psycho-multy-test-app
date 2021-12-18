package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.user.ChangePersonalInfoDto;
import vlsu.psycho.serverside.dto.user.ChangeablePersonalInfo;
import vlsu.psycho.serverside.dto.user.PersonalInfoDto;
import vlsu.psycho.serverside.dto.user.RegistrationDto;
import vlsu.psycho.serverside.model.Gender;
import vlsu.psycho.serverside.model.RoleTitle;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.UserRepository;
import vlsu.psycho.serverside.service.AuthService;
import vlsu.psycho.serverside.service.RoleService;
import vlsu.psycho.serverside.service.UserService;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;
import vlsu.psycho.serverside.utils.mappers.RegistrationMapper;
import vlsu.psycho.serverside.utils.mappers.dto.RegistrationMappingDto;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.RegistrationValidationDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Map<ChangeablePersonalInfo, ChangePersonalInfoFunction> CHANGE_PERSONAL_INFO_FUNCTIONS = Map.of(
        ChangeablePersonalInfo.NAME, User::setName,
        ChangeablePersonalInfo.SURNAME, User::setSurname,
        ChangeablePersonalInfo.MIDDLE_NAME, User::setMiddleName,
        ChangeablePersonalInfo.EMAIL, User::setEmail,
        ChangeablePersonalInfo.GENDER, (user, gender) -> user.setGender(Gender.valueOf(gender))
    );

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final Validator<RegistrationValidationDto> registrationValidator;
    private final RegistrationMapper registrationMapper;
    private final JwtProvider jwtProvider;
    private final Validator<ChangePersonalInfoDto> changePersonalInfoValidator;

    @Override
    @Transactional
    public void register(RegistrationDto registrationDto) {
        registrationValidator.validate(
                new RegistrationValidationDto()
                        .setLogin(registrationDto.getLogin())
                        .setPassword(registrationDto.getPassword())
                        .setLoginExists(repository.existsByLogin(registrationDto.getLogin()))
        );
        registrationDto.setPassword(encoder.encode(registrationDto.getPassword()));
        User user = registrationMapper.from(
                new RegistrationMappingDto()
                        .setDto(registrationDto)
                        .setRole(roleService.findByTitle(RoleTitle.ROLE_CLIENT))
        );
        repository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    @Override
    public User findByExternalId(UUID externalId) {
        return repository.findByExternalId(externalId);
    }

    @Override
    @Transactional
    public List<User> getClientsOfUser() {
        UUID externalId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());;
        User user = findByExternalId(externalId);
        return repository.findAllByPsychologist(user);
    }

    @Override
    @Transactional
    public void changePersonalInfo(ChangePersonalInfoDto changePersonalInfoDto) {
        changePersonalInfoValidator.validate(changePersonalInfoDto);
        Map<ChangeablePersonalInfo, String> map = changePersonalInfoDto.getInfoToChange();
        User user = findByExternalId(UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString()));
        CHANGE_PERSONAL_INFO_FUNCTIONS.forEach((key, value) -> {
            if (map.containsKey(key)) {
                value.change(user, map.get(key));
            }
        });
    }

    @Override
    public PersonalInfoDto getPersonalInfo() {
        UUID clientId = UUID.fromString(jwtProvider.getClaimFromToken(Claim.EXTERNAL_ID).toString());
        User user = repository.findByExternalId(clientId);
        return new PersonalInfoDto()
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setSurname(user.getSurname())
                .setMiddleName(user.getMiddleName())
                .setGender(user.getGender());
    }

    @FunctionalInterface
    private interface ChangePersonalInfoFunction {
        void change(User user, String newInfo);
    }
}
