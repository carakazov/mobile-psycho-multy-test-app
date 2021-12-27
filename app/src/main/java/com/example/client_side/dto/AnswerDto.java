package com.example.client_side.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class AnswerDto implements Serializable {
    private String text;
    private UUID externalId;
}
