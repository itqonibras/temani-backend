package com.temani.temani.features.relationship.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.relationship.infrastructure.mapper.RelationshipEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RelationshipRepositoryImpl implements RelationshipRepository {

    private final RelationshipJpaRepository jpa;
    private final RelationshipEntityMapper mapper;

    @Override
    public Relationship save(Relationship relationship) {
        RelationshipEntity savedEntity = jpa.save(mapper.toEntity(relationship));
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Relationship relationship) {
        jpa.delete(mapper.toEntity(relationship));
    }

    @Override
    public boolean existsByClientId(UUID clientId) {
        return jpa.existsByClientId(clientId);
    }

    @Override
    public boolean existsByCaregiverId(UUID caregiverId) {
        return jpa.existsByCaregiverId(caregiverId);
    }

    @Override
    public boolean existsByCaregiverIdAndClientIdNot(UUID targetId, UUID currentUserId) {
        return jpa.existsByCaregiverIdAndClientIdNot(targetId, currentUserId);
    }

    @Override
    public Optional<Relationship> findById(UUID id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Relationship> findByClientIdAndCaregiverId(UUID clientId, UUID caregiverId) {
        return jpa.findByClientIdAndCaregiverId(clientId, caregiverId).map(mapper::toDomain);
    }

    @Override
    public List<Relationship> findAcceptedByUserId(UUID userId) {
        return jpa.findAcceptedByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Relationship> findPendingSentByUserId(UUID userId) {
        return jpa.findAllByInitiatorIdAndAccepted(userId, false)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Relationship> findPendingReceivedByUserId(UUID userId) {
        return jpa.findPendingReceivedByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
