package com.example.client_side.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CustomAnswerDto implements Serializable {
    private String text;
    private Double value;
}
