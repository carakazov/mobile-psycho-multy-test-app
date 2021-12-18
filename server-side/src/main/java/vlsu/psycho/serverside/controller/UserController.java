package vlsu.psycho.serverside.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vlsu.psycho.serverside.dto.user.ChangePersonalInfoDto;
import vlsu.psycho.serverside.dto.user.PersonalInfoDto;
import vlsu.psycho.serverside.dto.user.RegistrationDto;
import vlsu.psycho.serverside.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public void register(@RequestBody @Valid RegistrationDto registrationDto) {
        userService.register(registrationDto);
    }

    @PostMapping("/update")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public void changePersonalInfo(@RequestBody ChangePersonalInfoDto changePersonalInfoDto) {
        userService.changePersonalInfo(changePersonalInfoDto);
    }

    @GetMapping("/info")
    @Secured({"ROLE_CLIENT", "ROLE_PSYCHOLOGIST"})
    public PersonalInfoDto getPersonalInfo() {
        return userService.getPersonalInfo();
    }
}
