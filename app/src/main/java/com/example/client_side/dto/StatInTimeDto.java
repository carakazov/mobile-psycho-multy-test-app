package com.example.client_side.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class StatInTimeDto implements Serializable {
    private String testTitle;
    private Map<String, String> resultsInTime;
}
