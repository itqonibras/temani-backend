package com.temani.temani.features.userrelationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.domain.repository.RelationshipRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteRelationshipUseCaseImpl implements DeleteRelationshipUseCase {

    private final RelationshipRepository relationshipRepository;

    @Override
    public void execute(UUID relationId, User user) {
        Relationship relationship = relationshipRepository.findById(relationId)
                .orElseThrow(() -> new IllegalArgumentException("Relationship not found!"));

        UUID userId = user.getId();

        boolean isCaregiver = userId.equals(relationship.getCaregiverId());

        if (!isCaregiver) {
            throw new IllegalStateException("You are not allowed to delete this relationship!");
        }

        relationshipRepository.delete(relationship);
    }

}
