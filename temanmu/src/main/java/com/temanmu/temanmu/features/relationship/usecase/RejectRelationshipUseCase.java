package com.temanmu.temanmu.features.relationship.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.relationship.presentation.dto.request.UpdateRelationshipStatusRequest;

public interface RejectRelationshipUseCase {

	void execute(UpdateRelationshipStatusRequest request, UUID relationId, User user);

}
