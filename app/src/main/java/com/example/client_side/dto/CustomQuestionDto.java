package com.example.client_side.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CustomQuestionDto implements Serializable {
    private String text;
    private List<CustomAnswerDto> answers;
}
