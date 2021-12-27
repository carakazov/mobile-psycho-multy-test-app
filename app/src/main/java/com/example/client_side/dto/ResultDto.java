package com.example.client_side.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultDto implements Serializable {
    private String text;
    private Number value;
}
