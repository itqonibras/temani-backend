package com.temani.temani.features.userrelationship.usecase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.userrelationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAcceptedRelationshipUseCaseImpl implements GetAcceptedRelationshipUseCase {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipDtoMapper mapper;

    @Override
    public List<RelationshipResponse> execute(UUID userId) {
        List<Relationship> relationships = relationshipRepository.findAcceptedByUserId(userId);
        return relationships.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
