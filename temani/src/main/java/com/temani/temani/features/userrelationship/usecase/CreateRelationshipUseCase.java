package com.temani.temani.features.userrelationship.usecase;

import java.util.Set;
import java.util.UUID;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.userrelationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;

public interface CreateRelationshipUseCase {
    RelationshipResponse execute(RelationshipRequest request, UUID userId, Set<Role> roles);
}
