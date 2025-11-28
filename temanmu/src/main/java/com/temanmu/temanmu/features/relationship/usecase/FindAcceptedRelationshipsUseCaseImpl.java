package com.temanmu.temanmu.features.relationship.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;
import com.temanmu.temanmu.features.relationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temanmu.temanmu.features.relationship.presentation.dto.response.RelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindAcceptedRelationshipsUseCaseImpl implements FindAcceptedRelationshipsUseCase {

	private final RelationshipRepository relationshipRepository;

	private final RelationshipDtoMapper mapper;

	@Override
	public List<RelationshipResponse> execute(UUID userId) {
		List<Relationship> relationships = relationshipRepository.findAcceptedByUserId(userId);
		return relationships.stream().map(mapper::toDto).toList();
	}

}
