package com.temanmu.temanmu.features.interactionlog.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.interactionlog.domain.model.InteractionLog;

public interface CreateInteractionLogUseCase {
    InteractionLog execute(UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description);
}