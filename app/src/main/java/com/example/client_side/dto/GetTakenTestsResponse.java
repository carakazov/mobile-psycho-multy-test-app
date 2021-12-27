package com.example.client_side.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GetTakenTestsResponse implements Serializable {
    private List<TakenTestsItemDto> tests;
}
