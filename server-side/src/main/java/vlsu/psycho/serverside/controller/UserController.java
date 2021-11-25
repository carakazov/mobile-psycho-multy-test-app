package vlsu.psycho.serverside.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vlsu.psycho.serverside.dto.user.ChangePersonalInfoDto;
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

    @GetMapping("/test")
    @PreAuthorize("permitAll()")
    public Parent getTest() {
        Child child = new Child();
        child.setChildString("this is child");
        child.setParentString("this is parent");
        return child;
    }

    @Data
    public static class Parent {
        protected String parentString;
    }

    @Data
    public static class Child extends Parent {
        protected String childString;
    }
}
