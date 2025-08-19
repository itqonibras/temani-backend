package com.temani.temani.features.interactionlog.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;
import com.temani.temani.features.interactionlog.domain.repository.InteractionLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateInteractionLogUseCaseImpl implements CreateInteractionLogUseCase {

    private final InteractionLogRepository interactionLogRepository;

    @Override
    public InteractionLog execute(UUID userId, String feature, String action, String entityType, UUID entityId,
            String title, String description) {
        System.out.println("CreateInteractionLogUseCase: Creating interaction log...");

        InteractionLog interactionLog = new InteractionLog(
                null, // Let JPA generate the ID
                userId,
                feature,
                action,
                entityType,
                entityId,
                title,
                description,
                LocalDateTime.now());

        System.out.println("CreateInteractionLogUseCase: About to save interaction log...");
        InteractionLog saved = interactionLogRepository.save(interactionLog);
        System.out.println("CreateInteractionLogUseCase: Saved interaction log with ID: " + saved.getId());

        return saved;
    }
}