package com.temani.temani.features.relationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.common.constants.RelationshipMessages;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRelationshipUseCaseImpl implements DeleteRelationshipUseCase {

    private final RelationshipRepository relationshipRepository;

    @Override
    public void execute(UUID relationId, User user) {
        Relationship relationship = relationshipRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException(RelationshipMessages.RELATIONSHIP_NOT_FOUND));

        UUID userId = user.getId();

        boolean isCaregiver = userId.equals(relationship.getCaregiverId());

        if (!isCaregiver) {
            throw new IllegalStateException(RelationshipMessages.NOT_ALLOWED_DELETE);
        }

        relationshipRepository.delete(relationship);
    }

}
