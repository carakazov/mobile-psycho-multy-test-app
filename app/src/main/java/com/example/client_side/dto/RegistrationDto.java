package com.example.client_side.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RegistrationDto {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String middleName;
    private String gender;
    private String email;
}
