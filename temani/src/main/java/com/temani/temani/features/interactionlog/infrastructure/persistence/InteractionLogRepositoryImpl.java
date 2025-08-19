package com.temani.temani.features.interactionlog.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;
import com.temani.temani.features.interactionlog.domain.repository.InteractionLogRepository;
import com.temani.temani.features.interactionlog.infrastructure.mapper.InteractionLogEntityMapper;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InteractionLogRepositoryImpl implements InteractionLogRepository {

    private final InteractionLogJpaRepository jpaRepository;
    private final InteractionLogEntityMapper mapper;
    private final UserJpaRepository userJpaRepository;

    @Override
    public InteractionLog save(InteractionLog interactionLog) {
        System.out.println("Repository: Saving interaction log for user: " + interactionLog.getUserId());

        try {
            // Convert domain to entity
            InteractionLogEntity entity = mapper.toEntity(interactionLog);

            // Get user reference
            UserEntity user = userJpaRepository.findById(interactionLog.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + interactionLog.getUserId()));
            entity.setUser(user);

            // Save using standard JPA method
            InteractionLogEntity savedEntity = jpaRepository.save(entity);

            System.out.println("Repository: Saved interaction log with ID: " + savedEntity.getId());
            return mapper.toDomain(savedEntity);
        } catch (Exception e) {
            System.err.println("Failed to save interaction log: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<InteractionLog> findAllByUserId(UUID userId) {
        System.out.println("Repository: Finding interaction logs for user: " + userId);
        List<InteractionLogEntity> entities = jpaRepository.findAllByUser_IdOrderByTimestampDesc(userId);
        System.out.println("Repository: Found " + entities.size() + " entities");
        return entities.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<InteractionLog> findAllByUserIdAndFeature(UUID userId, String feature) {
        List<InteractionLogEntity> entities = jpaRepository.findAllByUser_IdAndFeatureOrderByTimestampDesc(userId,
                feature);
        return entities.stream().map(mapper::toDomain).toList();
    }
}