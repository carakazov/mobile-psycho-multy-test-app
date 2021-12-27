package com.example.client_side.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String token;
    private String role;
}
