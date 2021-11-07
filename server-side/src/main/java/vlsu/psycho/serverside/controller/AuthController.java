package vlsu.psycho.serverside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vlsu.psycho.serverside.dto.auth.AuthRequestDto;
import vlsu.psycho.serverside.dto.auth.AuthResponseDto;
import vlsu.psycho.serverside.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    @PreAuthorize("permitAll()")
    public AuthResponseDto authenticate(@RequestBody @Valid AuthRequestDto authRequestDto) {
        return authService.createToken(authRequestDto);
    }
}
