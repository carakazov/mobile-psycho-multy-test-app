package com.example.client_side.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class TestDto implements Serializable {
    private String title;
    private UUID externalId;
    private String description;
    private Boolean isGenderDepending;
    private Boolean isReAnswerEnabled;
    private List<QuestionDto> questions;
}
