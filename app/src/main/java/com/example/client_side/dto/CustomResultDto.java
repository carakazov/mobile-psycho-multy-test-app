package com.example.client_side.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomResultDto implements Serializable {
    private String text;
    private Double minBorder;
    private Double maxBorder;
    private String languageCode;
}
