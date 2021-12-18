package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.user.ChangePersonalInfoDto;
import vlsu.psycho.serverside.dto.user.PersonalInfoDto;
import vlsu.psycho.serverside.dto.user.RegistrationDto;
import vlsu.psycho.serverside.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void register(RegistrationDto registrationDto);
    User findByLogin(String login);
    User findByExternalId(UUID externalId);
    List<User> getClientsOfUser();
    void changePersonalInfo(ChangePersonalInfoDto changePersonalInfoDto);
    PersonalInfoDto getPersonalInfo();
}
