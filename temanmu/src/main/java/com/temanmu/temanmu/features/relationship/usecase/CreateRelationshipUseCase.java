package com.temanmu.temanmu.features.relationship.usecase;

import java.util.Set;
import java.util.UUID;

import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.relationship.presentation.dto.request.RelationshipRequest;
import com.temanmu.temanmu.features.relationship.presentation.dto.response.RelationshipResponse;

public interface CreateRelationshipUseCase {

	RelationshipResponse execute(RelationshipRequest request, UUID userId, Set<Role> roles);

}
