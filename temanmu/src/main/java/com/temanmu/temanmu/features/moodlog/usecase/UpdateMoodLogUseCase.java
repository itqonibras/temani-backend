package com.temanmu.temanmu.features.moodlog.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;

public interface UpdateMoodLogUseCase {
    MoodLogResponse execute(UUID id, MoodLogRequest request, UUID userId);
}