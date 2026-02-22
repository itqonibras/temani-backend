package com.temanmu.temanmu.features.relationship.usecase;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.constants.RelationshipMessages;
import com.temanmu.temanmu.common.util.RoleUtils;
import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;
import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;
import com.temanmu.temanmu.features.relationship.infrastructure.mapper.RelationshipDtoMapper;
import com.temanmu.temanmu.features.relationship.presentation.dto.request.RelationshipRequest;
import com.temanmu.temanmu.features.relationship.presentation.dto.response.RelationshipResponse;

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
			.orElseThrow(() -> new IllegalArgumentException(RelationshipMessages.TARGET_NOT_FOUND));

		Relationship savedRelationship = null;

		if (RoleUtils.hasRole(roles, "CLIENT")) {
			// Check if client already has a caregiver
			if (relationshipRepository.existsByClientId(userId)) {
				throw new IllegalStateException(RelationshipMessages.CLIENT_ALREADY_HAS_CAREGIVER);
			}
			// Check if relationship already exists between this client and caregiver
			if (relationshipRepository.findByClientIdAndCaregiverId(userId, targetUser.getId()).isPresent()) {
				throw new IllegalStateException(RelationshipMessages.RELATIONSHIP_ALREADY_EXISTS);
			}
			Relationship relationship = new Relationship(null, userId, null, null, targetUser.getId(), null, null, userId, false, null, null);
			savedRelationship = relationshipRepository.save(relationship);
		}
		else if (RoleUtils.hasRole(roles, "CAREGIVER")) {
			// Check if target client already has a caregiver
			if (relationshipRepository.existsByClientId(targetUser.getId())) {
				throw new IllegalStateException(RelationshipMessages.CLIENT_ALREADY_HAS_CAREGIVER);
			}
			// Check if relationship already exists between this caregiver and client
			if (relationshipRepository.findByClientIdAndCaregiverId(targetUser.getId(), userId).isPresent()) {
				throw new IllegalStateException(RelationshipMessages.RELATIONSHIP_ALREADY_EXISTS);
			}
			Relationship relationship = new Relationship(null, targetUser.getId(), null, null, userId, null, null, userId, false, null, null);
			savedRelationship = relationshipRepository.save(relationship);
		}

		return mapper.toDto(savedRelationship);
	}

}
