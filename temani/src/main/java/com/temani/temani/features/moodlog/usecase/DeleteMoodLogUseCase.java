package com.temani.temani.features.moodlog.usecase;

import java.util.UUID;

public interface DeleteMoodLogUseCase {

	void execute(UUID moodLogId, UUID userId);

} 