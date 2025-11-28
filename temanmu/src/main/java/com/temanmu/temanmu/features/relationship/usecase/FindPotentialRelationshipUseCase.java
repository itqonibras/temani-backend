package com.temanmu.temanmu.features.relationship.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.relationship.presentation.dto.response.PotentialRelationshipResponse;

public interface FindPotentialRelationshipUseCase {

	List<PotentialRelationshipResponse> execute(String role, String keyword, UUID currentUserId);

}
