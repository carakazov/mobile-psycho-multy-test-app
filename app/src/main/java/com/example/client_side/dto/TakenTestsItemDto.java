package com.example.client_side.dto;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class TakenTestsItemDto implements Serializable {
    private String title;
    private UUID externalId;
}
