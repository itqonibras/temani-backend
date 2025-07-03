package com.temani.temani.features.moodlog.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteMoodLogUseCaseImpl implements DeleteMoodLogUseCase {

	private final MoodLogRepository moodLogRepository;
	private final MoodLogJpaRepository moodLogJpaRepository;

	@Override
	public void execute(UUID moodLogId, UUID userId) {
		MoodLog moodLog = moodLogRepository.findById(moodLogId)
			.orElseThrow(() -> new RuntimeException("Mood log not found"));
		
		if (!moodLog.getUserId().equals(userId)) {
			throw new RuntimeException("You can only delete your own mood logs");
		}
		
		moodLogRepository.delete(moodLog);
	}

} 