package com.temani.temani.features.moodlog.usecase;

import java.time.LocalDate;
import java.util.UUID;

import com.temani.temani.features.moodlog.presentation.dto.response.MoodSummaryResponse;

public interface GetMoodSummaryUseCase {

    MoodSummaryResponse execute(UUID userId, LocalDate weekStart);

}
