package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.dto.auth.AuthRequestDto;
import vlsu.psycho.serverside.dto.auth.AuthResponseDto;
import vlsu.psycho.serverside.model.User;
import vlsu.psycho.serverside.service.AuthService;
import vlsu.psycho.serverside.service.UserService;
import vlsu.psycho.serverside.utils.exception.BusinessException;
import vlsu.psycho.serverside.utils.exception.ErrorCode;
import vlsu.psycho.serverside.utils.jwt.Claim;
import vlsu.psycho.serverside.utils.jwt.JwtProvider;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final PasswordEncoder encoder;

    @Override
    public AuthResponseDto createToken(AuthRequestDto requestDto) {
        User user = userService.findByLogin(requestDto.getLogin());;
        if(Objects.nonNull(user) && encoder.matches(requestDto.getPassword(), user.getPassword())) {
            return new AuthResponseDto().setToken(jwtProvider.generateToken(user));
        } else {
            throw new BusinessException().setCode(ErrorCode.WRONG_CREDENTIALS);
        }
    }
}
