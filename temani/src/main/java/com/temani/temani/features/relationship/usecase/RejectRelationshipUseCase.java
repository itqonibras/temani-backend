package com.temani.temani.features.relationship.usecase;

import java.util.UUID;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.relationship.presentation.dto.request.UpdateRelationshipStatusRequest;

public interface RejectRelationshipUseCase {
    void execute(UpdateRelationshipStatusRequest request, UUID relationId, User user);
}
