package com.temani.temani.features.relationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.common.constants.RelationshipMessages;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.relationship.domain.model.Relationship;
import com.temani.temani.features.relationship.domain.repository.RelationshipRepository;
import com.temani.temani.features.relationship.presentation.dto.request.UpdateRelationshipStatusRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RejectRelationshipUseCaseImpl implements RejectRelationshipUseCase {

	private final RelationshipRepository relationshipRepository;

	@Override
	public void execute(UpdateRelationshipStatusRequest request, UUID relationId, User user) {
		Relationship existingRelationship = relationshipRepository.findById(relationId)
			.orElseThrow(() -> new IllegalArgumentException(RelationshipMessages.RELATIONSHIP_NOT_FOUND));

		UUID userId = user.getId();
		UUID initiatorId = existingRelationship.getInitiatorId();

		boolean isClient = userId.equals(existingRelationship.getClientId());
		boolean isCaregiver = userId.equals(existingRelationship.getCaregiverId());

		if (!isClient && !isCaregiver) {
			throw new IllegalStateException(RelationshipMessages.NOT_PART_OF_RELATIONSHIP);
		}

		if (existingRelationship.isAccepted()) {
			throw new IllegalStateException(RelationshipMessages.NOT_ALLOWED_REJECT_ACCEPTED);
		}

		if (userId.equals(initiatorId)) {
			throw new IllegalStateException(RelationshipMessages.CANNOT_REJECT_OWN_REQUEST);
		}

		relationshipRepository.delete(existingRelationship);
	}

}
