package com.temani.temani.features.interactionlog.infrastructure.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;
import com.temani.temani.features.interactionlog.infrastructure.persistence.InteractionLogEntity;

@Component
public class InteractionLogEntityMapper {

    public InteractionLog toDomain(InteractionLogEntity entity) {
        return new InteractionLog(
                entity.getId(),
                entity.getUser().getId(),
                entity.getFeature(),
                entity.getAction(),
                entity.getEntityType(),
                entity.getEntityId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getTimestamp());
    }

    public InteractionLogEntity toEntity(InteractionLog domain) {
        // Note: This method would need UserEntity injection for full implementation
        // For now, we'll use it only for domain to entity conversion in repository
        InteractionLogEntity entity = new InteractionLogEntity();
        // Don't set ID - let JPA generate it
        entity.setFeature(domain.getFeature());
        entity.setAction(domain.getAction());
        entity.setEntityType(domain.getEntityType());
        entity.setEntityId(domain.getEntityId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setTimestamp(domain.getTimestamp());
        return entity;
    }
}