package com.temani.temani.features.relationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.relationship.presentation.dto.request.UpdateRelationshipStatusRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CancelRelationshipUseCaseImpl implements CancelRelationshipUseCase {
    
    private final RelationshipRepository relationshipRepository;

    @Override
    public void execute(UpdateRelationshipStatusRequest request, UUID relationId, User user) {
        Relationship existingRelationship = relationshipRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException("Relationship not found!"));

        UUID userId = user.getId();
        UUID initiatorId = existingRelationship.getInitiatorId();

        boolean isClient = userId.equals(existingRelationship.getClientId());
        boolean isCaregiver = userId.equals(existingRelationship.getCaregiverId());

        if (!isClient && !isCaregiver) {
            throw new IllegalAccessError("You are not part of this relationship!");
        }

        if (existingRelationship.isAccepted()) {
            throw new IllegalStateException("You are not allowed to cancel accepted relationship!");
        }

        if (!userId.equals(initiatorId)) {
            throw new IllegalStateException("You are not allowed to cancel this relationship!");
        }

        relationshipRepository.delete(existingRelationship);
    }

}
