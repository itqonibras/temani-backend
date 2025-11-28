package com.temanmu.temanmu.features.relationship.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.constants.RelationshipMessages;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;

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
