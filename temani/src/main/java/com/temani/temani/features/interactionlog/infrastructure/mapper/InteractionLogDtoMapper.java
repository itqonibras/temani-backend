package com.temani.temani.features.interactionlog.infrastructure.mapper;

import org.springframework.stereotype.Component;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;
import com.temani.temani.features.interactionlog.presentation.dto.response.InteractionLogResponse;

@Component
public class InteractionLogDtoMapper {

    public InteractionLogResponse toDto(InteractionLog domain) {
        return new InteractionLogResponse(
                domain.getId(),
                domain.getUserId(),
                domain.getFeature(),
                domain.getAction(),
                domain.getEntityType(),
                domain.getEntityId(),
                domain.getTitle(),
                domain.getDescription(),
                domain.getTimestamp());
    }
}