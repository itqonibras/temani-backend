package com.temani.temani.features.relationship.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRelationshipStatusRequest {
    
    @NotBlank(message = "Status can't be empty!")
    private String status;

}
