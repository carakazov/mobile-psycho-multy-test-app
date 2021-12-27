package com.example.client_side.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ChangePersonalInfoDto {
    private Map<ChangePersonalInfoParameter, String> infoToChange;
}
