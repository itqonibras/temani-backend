package com.temani.temani.features.relationship.usecase;

import java.util.UUID;

import com.temani.temani.features.profile.domain.model.User;

public interface DeleteRelationshipUseCase {
    void execute(UUID relationId, User user);
}
