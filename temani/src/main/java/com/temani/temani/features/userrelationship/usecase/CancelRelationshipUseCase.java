package com.temani.temani.features.userrelationship.usecase;

import java.util.UUID;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.presentation.dto.request.UpdateRelationshipStatusRequest;

public interface CancelRelationshipUseCase {
    void execute(UpdateRelationshipStatusRequest request, UUID relationId, User user);
}
