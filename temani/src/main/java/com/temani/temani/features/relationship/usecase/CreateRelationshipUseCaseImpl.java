package com.temani.temani.features.relationship.usecase;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.relationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temani.temani.features.relationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.relationship.presentation.dto.response.RelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateRelationshipUseCaseImpl implements CreateRelationshipUseCase {

    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;
    private final RelationshipDtoMapper mapper;

    @Override
    public RelationshipResponse execute(RelationshipRequest request, UUID userId, Set<Role> roles) {
        User targetUser = userRepository.findById(request.getTargetId())
                .orElseThrow(() -> new IllegalArgumentException("Target not found!"));

        Relationship savedRelationship = null;

        if (hasRole(roles, "CLIENT")) {
            if (relationshipRepository.existsByCaregiverId(targetUser.getId())) {
                throw new IllegalStateException("This caregiver already had client!");
            }
            Relationship relationship = new Relationship(
                    null,
                    userId,
                    targetUser.getId(),
                    userId,
                    false,
                    null,
                    null);
            savedRelationship = relationshipRepository.save(relationship);
        } else if (hasRole(roles, "CAREGIVER")) {
            if (relationshipRepository.existsByCaregiverId(userId)) {
                throw new IllegalStateException("This caregiver already had client!");
            }
            Relationship relationship = new Relationship(
                    null,
                    targetUser.getId(),
                    userId,
                    userId,
                    false,
                    null,
                    null);
            savedRelationship = relationshipRepository.save(relationship);
        }

        return mapper.toDto(savedRelationship);
    }

    private static boolean hasRole(Set<Role> roles, String roleName) {
        return roles.stream()
                .anyMatch(role -> roleName.equalsIgnoreCase(role.getName()));
    }
}
