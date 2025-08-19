package com.temani.temani.features.interactionlog.domain.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.interactionlog.usecase.CreateInteractionLogUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InteractionLogServiceImpl implements InteractionLogService {

    private final CreateInteractionLogUseCase createInteractionLogUseCase;

    @Override
    public void logInteraction(UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description) {
        try {
            System.out.println("=== INTERACTION LOGGING START ===");
            System.out.println("Logging interaction - User: " + userId + ", Feature: " + feature
                    + ", Action: " + action + ", Title: " + title);
            System.out.println("Entity Type: " + entityType + ", Entity ID: " + entityId);
            System.out.println("Description: " + description);

            createInteractionLogUseCase.execute(userId, feature, action, entityType,
                    entityId, title, description);

            System.out.println("Interaction logged successfully");
            System.out.println("=== INTERACTION LOGGING END ===");
        } catch (Exception e) {
            // Log the error but don't throw it to avoid breaking the main operation
            System.err.println("=== INTERACTION LOGGING ERROR ===");
            System.err.println("Failed to log interaction: " + e.getMessage());
            e.printStackTrace();
            System.err.println("=== END ERROR ===");
        }
    }
}