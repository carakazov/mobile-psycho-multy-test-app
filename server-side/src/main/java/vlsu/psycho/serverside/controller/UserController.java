package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.RegistrationDto;
import vlsu.psycho.serverside.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void register(@RequestBody @Valid RegistrationDto registrationDto) {
        userService.register(registrationDto);
    }
}
