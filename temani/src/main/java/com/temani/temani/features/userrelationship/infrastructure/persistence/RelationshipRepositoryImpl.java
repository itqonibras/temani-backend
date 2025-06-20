package com.temani.temani.features.userrelationship.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.userrelationship.infrastructure.mapper.RelationshipEntityMapper;

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
    public boolean existsByClientId(UUID clientId) {
        return jpa.existsByClientId(clientId);
    }

    @Override
    public boolean existsByCaregiverId(UUID caregiverId) {
        return jpa.existsByCaregiverId(caregiverId);
    }

    @Override
    public List<Relationship> findAcceptedByUserId(UUID userId) {
        return jpa.findAcceptedByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Relationship> findPendingReceivedByUserId(UUID userId) {
        return jpa.findAllByInitiatorIdAndAccepted(userId, false)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Relationship> findPendingSentByUserId(UUID userId) {
        return jpa.findPendingReceivedByUserId(userId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

}
