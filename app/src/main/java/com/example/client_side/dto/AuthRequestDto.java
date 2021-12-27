package com.example.client_side.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequestDto {
    private String login;
    private String password;
}
