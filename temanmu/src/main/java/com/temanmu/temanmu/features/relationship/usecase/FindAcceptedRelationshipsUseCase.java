package com.temanmu.temanmu.features.relationship.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.relationship.presentation.dto.response.RelationshipResponse;

public interface FindAcceptedRelationshipsUseCase {

	List<RelationshipResponse> execute(UUID userId);

}
