package com.temani.temani.features.userrelationship.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.userrelationship.domain.model.Relationship;
import com.temani.temani.features.userrelationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.userrelationship.presentation.dto.response.PotentialRelationshipResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindPotentialRelationshipUseCaseImpl implements FindPotentialRelationshipUseCase {

    private final UserRepository userRepository;
    private final RelationshipRepository relationshipRepository;

    @Override
    public List<PotentialRelationshipResponse> execute(String role, String keyword, UUID currentUserId) {
        List<PotentialRelationshipResponse> potentialRelationships = new ArrayList<>();

        List<User> users = userRepository.findAllByRoleAndKeyword(role, keyword, currentUserId);

        for (User target : users) {
            UUID targetId = target.getId();

            Optional<Relationship> sent = relationshipRepository.findByClientIdAndCaregiverId(currentUserId, targetId);
            Optional<Relationship> received = relationshipRepository.findByClientIdAndCaregiverId(targetId,
                    currentUserId);

            String status;

            if (sent.isPresent() && sent.get().isAccepted()) {
                status = "CONNECTED";
            } else if (received.isPresent() && received.get().isAccepted()) {
                status = "CONNECTED";
            } else if (sent.isPresent()) {
                status = "SENT";
            } else if (received.isPresent()) {
                status = "RECEIVED";
            } else if (isConnectedToOther(target, currentUserId)) {
                status = "CONNECTED_TO_OTHER";
            } else {
                status = "NONE";
            }

            potentialRelationships.add(
                    new PotentialRelationshipResponse(
                            target.getId(),
                            target.getName(),
                            target.getUsername(),
                            target.getRoles(),
                            status));
        }

        return potentialRelationships;
    }

    private boolean isConnectedToOther(User target, UUID currentUserId) {
        UUID targetId = target.getId();

        if (hasRole(target.getRoles(), "CAREGIVER")) {
            return relationshipRepository.existsByCaregiverIdAndClientIdNot(targetId, currentUserId);
        }

        return false;
    }

    private boolean hasRole(Set<Role> roles, String roleName) {
        return roles.stream().anyMatch(r -> r.getName().equalsIgnoreCase(roleName));
    }

}
