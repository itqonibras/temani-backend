package com.temani.temani.features.userrelationship.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;

public interface GetAcceptedRelationshipsUseCase {
    List<RelationshipResponse> execute(UUID userId);
}
