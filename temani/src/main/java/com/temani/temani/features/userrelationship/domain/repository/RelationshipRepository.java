package com.temani.temani.features.userrelationship.domain.repository;

import java.util.UUID;

import com.temani.temani.features.userrelationship.domain.model.Relationship;

public interface RelationshipRepository {
    Relationship save(Relationship relationship);

    boolean existsByClientId(UUID clientId);

    boolean existsByCaregiverId(UUID caregiverId);
}
