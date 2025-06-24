package com.temani.temani.features.relationship.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipResponse {
    private UUID id;
    private UUID clientId;
    private UUID caregiverId;
    private UUID initiatorId;
    private boolean accepted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
