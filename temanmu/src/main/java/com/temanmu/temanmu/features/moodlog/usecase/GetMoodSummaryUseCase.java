package com.temanmu.temanmu.features.moodlog.usecase;

import java.time.LocalDate;
import java.util.UUID;

import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodSummaryResponse;

public interface GetMoodSummaryUseCase {

    MoodSummaryResponse execute(UUID userId, LocalDate weekStart);

}
