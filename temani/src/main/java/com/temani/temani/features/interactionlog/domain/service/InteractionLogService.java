package com.temani.temani.features.interactionlog.domain.service;

import java.util.UUID;

public interface InteractionLogService {
    void logInteraction(UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description);
}