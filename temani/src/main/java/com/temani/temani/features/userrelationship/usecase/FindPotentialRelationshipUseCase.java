package com.temani.temani.features.userrelationship.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.userrelationship.presentation.dto.response.PotentialRelationshipResponse;

public interface FindPotentialRelationshipUseCase {
    List<PotentialRelationshipResponse> execute(String role, String keyword, UUID currentUserId);
}
