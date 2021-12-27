package com.example.client_side.dto;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private List<ExceptionDto> exceptions;
}
