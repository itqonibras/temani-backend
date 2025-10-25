package com.temani.temani.features.moodlog.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogEntity;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogJpaRepository;
import com.temani.temani.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMoodLogUseCaseImpl implements UpdateMoodLogUseCase {

    private final MoodLogJpaRepository moodLogJpaRepository;
    private final MoodLogDtoMapper mapper;
    private final InteractionLogService interactionLogService;

    @Override
    public MoodLogResponse execute(UUID id, MoodLogRequest request, UUID userId) {
        // Fetch the existing mood log entity
        MoodLogEntity moodLogEntity = moodLogJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mood log not found"));

        // Check ownership
        if (!moodLogEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only update your own mood logs");
        }

        // Update fields
        moodLogEntity.setMoodVisual(request.getMoodVisual());
        moodLogEntity.setEmotionScale(request.getEmotionScale());
        moodLogEntity.setTimestamp(LocalDateTime.now());

        // Save
        MoodLogEntity saved = moodLogJpaRepository.save(moodLogEntity);

        // Map to domain model
        MoodLog moodLog = new MoodLog(
                saved.getId(),
                saved.getUser().getId(),
                saved.getMoodVisual(),
                saved.getEmotionScale(),
                saved.getTimestamp());

        // Log the interaction
        try {
            interactionLogService.logInteraction(
                    userId,
                    "moodlog",
                    "update",
                    "moodlog",
                    saved.getId(),
                    "Mengupdate Mood Tracker",
                    "Mengupdate mood: " + request.getMoodVisual());
        } catch (Exception e) {
            System.err.println("Failed to log moodlog update interaction: " + e.getMessage());
        }

        // Map to DTO
        return mapper.toDto(moodLog);
    }
}