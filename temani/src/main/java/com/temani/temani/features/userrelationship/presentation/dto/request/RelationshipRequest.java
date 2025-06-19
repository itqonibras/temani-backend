package com.temani.temani.features.userrelationship.presentation.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipRequest {
    
    @NotBlank(message = "Target ID can't be empty!")
    private UUID targetId;

}
