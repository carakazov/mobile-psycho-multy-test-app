package com.example.client_side.dto;

import java.util.UUID;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AnsweredQuestionDto {
    private UUID testQuestionId;
    private UUID testAnswerId;
}
