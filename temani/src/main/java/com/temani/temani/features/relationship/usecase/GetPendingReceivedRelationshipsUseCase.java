package com.temani.temani.features.relationship.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.relationship.presentation.dto.response.RelationshipResponse;

public interface GetPendingReceivedRelationshipsUseCase {
    List<RelationshipResponse> execute(UUID userId);
}
