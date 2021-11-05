package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.RegistrationDto;
import vlsu.psycho.serverside.model.RoleTitle;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.repository.UserRepository;
import vlsu.psycho.serverside.service.RoleService;
import vlsu.psycho.serverside.service.UserService;
import vlsu.psycho.serverside.utils.mappers.RegistrationMapper;
import vlsu.psycho.serverside.utils.mappers.dto.RegistrationMappingDto;
import vlsu.psycho.serverside.utils.validation.Validator;
import vlsu.psycho.serverside.utils.validation.dto.RegistrationValidationDto;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final RoleService roleService;
    private final Validator<RegistrationValidationDto> registrationValidator;
    private final RegistrationMapper registrationMapper;

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
                        .setRole(roleService.findByTitle(RoleTitle.CLIENT))
        );
        repository.save(user);
    }
}
