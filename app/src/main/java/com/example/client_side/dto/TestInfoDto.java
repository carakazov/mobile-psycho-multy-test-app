package com.example.client_side.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class TestInfoDto {
    private String title;
    private String expectedTime;
    private UUID externalId;
}
