package com.temani.temani.features.interactionlog.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionLogResponse {

    private UUID id;
    private UUID userId;
    private String feature;
    private String action;
    private String entityType;
    private UUID entityId;
    private String title;
    private String description;
    private LocalDateTime timestamp;

}