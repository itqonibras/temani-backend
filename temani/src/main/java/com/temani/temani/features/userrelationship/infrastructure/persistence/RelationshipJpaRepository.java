package com.temani.temani.features.userrelationship.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationshipJpaRepository extends JpaRepository<RelationshipEntity, UUID> {
    boolean existsByClientId(UUID clientId);

    boolean existsByCaregiverId(UUID caregiverId);

    boolean existsByCaregiverIdAndClientIdNot(UUID targetId, UUID currentUserId);

    Optional<RelationshipEntity> findById(UUID id);

    Optional<RelationshipEntity> findByClientIdAndCaregiverId(UUID clientId, UUID caregiverId);

    @Query("""
        SELECT r FROM RelationshipEntity r
        WHERE r.accepted = true AND (r.clientId = :userId OR r.caregiverId = :userId)
    """)
    List<RelationshipEntity> findAcceptedByUserId(@Param("userId") UUID userId);

    List<RelationshipEntity> findAllByInitiatorIdAndAccepted(UUID initiatorId, boolean accepted);

    @Query("""
        SELECT r FROM RelationshipEntity r
        WHERE r.accepted = false AND r.initiatorId <> :userId
          AND (r.clientId = :userId OR r.caregiverId = :userId)
    """)
    List<RelationshipEntity> findPendingReceivedByUserId(@Param("userId") UUID userId);
}
