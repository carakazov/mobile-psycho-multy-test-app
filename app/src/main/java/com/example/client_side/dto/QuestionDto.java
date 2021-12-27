package com.example.client_side.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class QuestionDto implements Serializable {
    private String text;
    private UUID externalId;

    private List<AnswerDto> answers;
}
