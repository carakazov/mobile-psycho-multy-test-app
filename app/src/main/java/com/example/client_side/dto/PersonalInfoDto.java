package com.example.client_side.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonalInfoDto implements Serializable {
    private String name;
    private String surname;
    private String middleName;
    private String gender;
    private String email;
}
