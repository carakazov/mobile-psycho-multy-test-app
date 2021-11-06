package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.user.RegistrationDto;
import vlsu.psycho.serverside.model.User;

public interface UserService {
    void register(RegistrationDto registrationDto);
    User findByLogin(String login);
}
