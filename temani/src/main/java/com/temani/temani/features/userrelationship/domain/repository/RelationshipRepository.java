package com.temani.temani.features.userrelationship.domain.repository;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.userrelationship.domain.model.Relationship;

public interface RelationshipRepository {
    Relationship save(Relationship relationship);

    boolean existsByClientId(UUID clientId);

    boolean existsByCaregiverId(UUID caregiverId);

    List<Relationship> findAcceptedByUserId(UUID userId);

    List<Relationship> findPendingSentByUserId(UUID userId);

    List<Relationship> findPendingReceivedByUserId(UUID userId);
}
