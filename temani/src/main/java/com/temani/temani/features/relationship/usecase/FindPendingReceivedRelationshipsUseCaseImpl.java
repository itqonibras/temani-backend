package com.temani.temani.features.relationship.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.relationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temani.temani.features.relationship.presentation.dto.response.RelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPendingReceivedRelationshipsUseCaseImpl implements FindPendingReceivedRelationshipsUseCase {
    
    private final RelationshipRepository relationshipRepository;
    private final RelationshipDtoMapper mapper;

    @Override
    public List<RelationshipResponse> execute(UUID userId) {
        List<Relationship> relationships = relationshipRepository.findPendingReceivedByUserId(userId);
        return relationships.stream()
                .map(mapper::toDto)
                .toList();
    }

}
