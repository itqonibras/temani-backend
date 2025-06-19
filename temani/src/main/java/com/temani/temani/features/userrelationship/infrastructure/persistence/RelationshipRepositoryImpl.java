package com.temani.temani.features.userrelationship.infrastructure.persistence;

import java.util.UUID;

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

    public boolean existsByClientId(UUID clientId) {
        return jpa.existsByClientId(clientId);
    }

    public boolean existsByCaregiverId(UUID caregiverId) {
        return jpa.existsByCaregiverId(caregiverId);
    }
}
