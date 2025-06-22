package com.temani.temani.features.userrelationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.userrelationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temani.temani.features.userrelationship.presentation.dto.request.UpdateRelationshipStatusRequest;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AcceptRelationshipUseCaseImpl implements AcceptRelationshipUseCase {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipDtoMapper mapper;

    @Override
    public RelationshipResponse execute(UpdateRelationshipStatusRequest request, UUID relationId, User user) {
        Relationship existingRelationship = relationshipRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException("Relationship not found!"));

        UUID userId = user.getId();
        UUID initiatorId = existingRelationship.getInitiatorId();

        boolean isClient = userId.equals(existingRelationship.getClientId());
        boolean isCaregiver = userId.equals(existingRelationship.getCaregiverId());

        if (!isClient && !isCaregiver) {
            throw new IllegalAccessError("You are not part of this relationship!");
        }

        if (userId.equals(initiatorId)) {
            throw new IllegalStateException("You cannot accept your own request!");
        }

        Relationship updatedRelationship = new Relationship(
                existingRelationship.getId(),
                existingRelationship.getClientId(),
                existingRelationship.getCaregiverId(),
                existingRelationship.getInitiatorId(),
                true,
                existingRelationship.getCreatedAt(),
                existingRelationship.getUpdatedAt());

        Relationship savedRelationship = relationshipRepository.save(updatedRelationship);
        return mapper.toDto(savedRelationship);
    }

}
