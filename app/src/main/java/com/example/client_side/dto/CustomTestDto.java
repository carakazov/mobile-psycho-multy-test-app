package com.example.client_side.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CustomTestDto implements Serializable {
    private String title;
    private String description;
    private String expectedTime;
    private String proceedingType;
    private String languageCode;


    private List<CustomQuestionDto> questions = new ArrayList<>();
    private List<CustomResultDto> results = new ArrayList<>();
}
