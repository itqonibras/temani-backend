package com.temani.temani.features.moodlog.usecase;

import java.util.UUID;

import com.temani.temani.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;

public interface CreateMoodLogUseCase {

	MoodLogResponse execute(MoodLogRequest request, UUID userId);

} 