package com.temani.temani.features.moodlog.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;

public interface GetAllMoodLogsUseCase {

	List<MoodLogResponse> execute(UUID userId);

} 