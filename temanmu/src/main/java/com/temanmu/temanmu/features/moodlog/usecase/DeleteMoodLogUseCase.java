package com.temanmu.temanmu.features.moodlog.usecase;

import java.util.UUID;

public interface DeleteMoodLogUseCase {

	void execute(UUID moodLogId, UUID userId);

}