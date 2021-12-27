package com.example.client_side.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import kotlinx.coroutines.internal.LocalAtomics_commonKt;
import lombok.Data;

@Data
public class TakenTestDto {
    private UUID testExternalId;
    private String startDate;
    private String languageCode;
    private String finishDate;
    private List<AnsweredQuestionDto> answers;

    @JsonIgnore
    private LocalDateTime startTime;
    @JsonIgnore
    private LocalDateTime finishTime;
}
