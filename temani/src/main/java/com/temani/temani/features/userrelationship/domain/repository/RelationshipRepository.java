package com.temani.temani.features.userrelationship.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.userrelationship.domain.model.Relationship;

public interface RelationshipRepository {
    Relationship save(Relationship relationship);

    void delete(Relationship relationship);

    boolean existsByClientId(UUID clientId);

    boolean existsByCaregiverId(UUID caregiverId);

    Optional<Relationship> findById(UUID id);

    List<Relationship> findAcceptedByUserId(UUID userId);

    List<Relationship> findPendingSentByUserId(UUID userId);

    List<Relationship> findPendingReceivedByUserId(UUID userId);
}
