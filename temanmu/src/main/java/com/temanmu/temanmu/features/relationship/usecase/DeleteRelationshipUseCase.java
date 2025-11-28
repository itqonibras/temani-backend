package com.temanmu.temanmu.features.relationship.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.profile.domain.model.User;

public interface DeleteRelationshipUseCase {

	void execute(UUID relationId, User user);

}
