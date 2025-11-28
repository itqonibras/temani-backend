package com.temanmu.temanmu.features.moodlog.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;

public interface GetAllMoodLogsUseCase {

	List<MoodLogResponse> execute(UUID userId);

}