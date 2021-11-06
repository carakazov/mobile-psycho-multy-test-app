package vlsu.psycho.serverside.service;

import vlsu.psycho.serverside.dto.auth.AuthRequestDto;
import vlsu.psycho.serverside.dto.auth.AuthResponseDto;

public interface AuthService {
    AuthResponseDto getToken(AuthRequestDto requestDto);
}
