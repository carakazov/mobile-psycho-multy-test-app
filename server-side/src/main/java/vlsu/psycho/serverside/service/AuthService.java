package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.auth.AuthRequestDto;
import vlsu.psycho.serverside.dto.auth.AuthResponseDto;

import java.util.UUID;

public interface AuthService {
    AuthResponseDto createToken(AuthRequestDto requestDto);
}
