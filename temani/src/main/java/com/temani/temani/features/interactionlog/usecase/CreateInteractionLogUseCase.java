package com.temani.temani.features.interactionlog.usecase;

import java.util.UUID;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;

public interface CreateInteractionLogUseCase {
    InteractionLog execute(UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description);
}