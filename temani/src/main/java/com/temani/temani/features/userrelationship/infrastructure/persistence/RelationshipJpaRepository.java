package com.temani.temani.features.userrelationship.infrastructure.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationshipJpaRepository extends JpaRepository<RelationshipEntity, UUID> {
    boolean existsByClientId(UUID clientId);

    boolean existsByCaregiverId(UUID caregiverId);
}
