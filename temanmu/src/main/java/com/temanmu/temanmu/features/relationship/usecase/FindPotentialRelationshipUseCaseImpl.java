package com.temanmu.temanmu.features.relationship.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.util.RoleUtils;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;
import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;
import com.temanmu.temanmu.features.relationship.presentation.dto.response.PotentialRelationshipResponse;

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
			}
			else if (received.isPresent() && received.get().isAccepted()) {
				status = "CONNECTED";
			}
			else if (sent.isPresent()) {
				status = "SENT";
			}
			else if (received.isPresent()) {
				status = "RECEIVED";
			}
			else if (isConnectedToOther(target, currentUserId)) {
				status = "CONNECTED_TO_OTHER";
			}
			else {
				status = "NONE";
			}

			potentialRelationships.add(new PotentialRelationshipResponse(target.getId(), target.getName(),
					target.getUsername(), target.getProfilePicture(), target.getRoles(), status));
		}

		return potentialRelationships;
	}

	private boolean isConnectedToOther(User target, UUID currentUserId) {
		UUID targetId = target.getId();

		// Check if target client already has a caregiver (not the current user)
		if (RoleUtils.hasRole(target.getRoles(), "CLIENT")) {
			// Check if client has ANY relationship (pending or accepted) with another caregiver.
			// This mirrors the existsByClientId guard in CreateRelationshipUseCaseImpl so that
			// the search result status accurately reflects whether a new request would be blocked.
			if (relationshipRepository.existsByClientId(targetId)) {
				// If the only existing relationship is with the current user, it is NOT "other"
				Optional<Relationship> withCurrentUser =
						relationshipRepository.findByClientIdAndCaregiverId(targetId, currentUserId);
				return withCurrentUser.isEmpty();
			}
		}

		return false;
	}

}
