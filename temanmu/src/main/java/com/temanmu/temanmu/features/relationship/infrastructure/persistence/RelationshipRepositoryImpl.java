package com.temanmu.temanmu.features.relationship.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temanmu.temanmu.features.relationship.domain.model.Relationship;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;
import com.temanmu.temanmu.features.relationship.infrastructure.mapper.RelationshipEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RelationshipRepositoryImpl implements RelationshipRepository {

	private final RelationshipJpaRepository jpa;

	private final RelationshipEntityMapper mapper;

	private final UserJpaRepository userJpaRepository;

	@Override
	public Relationship save(Relationship relationship) {
		RelationshipEntity savedEntity = jpa.save(mapper.toEntity(relationship));
		return mapToDomainWithNames(savedEntity);
	}

	@Override
	public void delete(Relationship relationship) {
		jpa.delete(mapper.toEntity(relationship));
	}

	@Override
	public boolean existsByClientId(UUID clientId) {
		return jpa.existsByClientId(clientId);
	}

	@Override
	public boolean existsByCaregiverId(UUID caregiverId) {
		return jpa.existsByCaregiverId(caregiverId);
	}

	@Override
	public boolean existsByCaregiverIdAndClientIdNot(UUID targetId, UUID currentUserId) {
		return jpa.existsByCaregiverIdAndClientIdNot(targetId, currentUserId);
	}

	@Override
	public Optional<Relationship> findById(UUID id) {
		return jpa.findById(id).map(this::mapToDomainWithNames);
	}

	@Override
	public Optional<Relationship> findByClientIdAndCaregiverId(UUID clientId, UUID caregiverId) {
		return jpa.findByClientIdAndCaregiverId(clientId, caregiverId).map(this::mapToDomainWithNames);
	}

	@Override
	public List<Relationship> findAcceptedByUserId(UUID userId) {
		List<RelationshipEntity> entities = jpa.findAcceptedByUserId(userId);
		return mapToDomainsWithNames(entities);
	}

	@Override
	public List<Relationship> findPendingSentByUserId(UUID userId) {
		List<RelationshipEntity> entities = jpa.findAllByInitiatorIdAndAccepted(userId, false);
		return mapToDomainsWithNames(entities);
	}

	@Override
	public List<Relationship> findPendingReceivedByUserId(UUID userId) {
		List<RelationshipEntity> entities = jpa.findPendingReceivedByUserId(userId);
		return mapToDomainsWithNames(entities);
	}

	private Relationship mapToDomainWithNames(RelationshipEntity entity) {
		Relationship domain = mapper.toDomain(entity);

		// Fetch user names
		String clientName = userJpaRepository.findById(entity.getClientId())
				.map(user -> user.getName())
				.orElse(null);
		String caregiverName = userJpaRepository.findById(entity.getCaregiverId())
				.map(user -> user.getName())
				.orElse(null);

		// Create new Relationship with names
		return new Relationship(
				domain.getId(),
				domain.getClientId(),
				clientName,
				domain.getCaregiverId(),
				caregiverName,
				domain.getInitiatorId(),
				domain.isAccepted(),
				domain.getCreatedAt(),
				domain.getUpdatedAt()
		);
	}

	private List<Relationship> mapToDomainsWithNames(List<RelationshipEntity> entities) {
		if (entities.isEmpty()) {
			return List.of();
		}

		// Collect all unique user IDs
		Set<UUID> userIds = entities.stream()
				.flatMap(e -> Set.of(e.getClientId(), e.getCaregiverId()).stream())
				.collect(Collectors.toSet());

		// Batch fetch all users
		Map<UUID, String> userNamesMap = userJpaRepository.findAllById(userIds).stream()
				.collect(Collectors.toMap(
						user -> user.getId(),
						user -> user.getName()
				));

		// Map entities to domain objects with names
		return entities.stream()
				.map(entity -> {
					Relationship domain = mapper.toDomain(entity);
					String clientName = userNamesMap.get(entity.getClientId());
					String caregiverName = userNamesMap.get(entity.getCaregiverId());
					return new Relationship(
							domain.getId(),
							domain.getClientId(),
							clientName,
							domain.getCaregiverId(),
							caregiverName,
							domain.getInitiatorId(),
							domain.isAccepted(),
							domain.getCreatedAt(),
							domain.getUpdatedAt()
					);
				})
				.toList();
	}

}
