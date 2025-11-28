package com.temanmu.temanmu.features.moodlog.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.interactionlog.domain.service.InteractionLogService;
import com.temanmu.temanmu.features.moodlog.domain.model.MoodLog;
import com.temanmu.temanmu.features.moodlog.domain.repository.MoodLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteMoodLogUseCaseImpl implements DeleteMoodLogUseCase {

	private final MoodLogRepository moodLogRepository;
	private final InteractionLogService interactionLogService;

	@Override
	public void execute(UUID moodLogId, UUID userId) {
		MoodLog moodLog = moodLogRepository.findById(moodLogId)
				.orElseThrow(() -> new RuntimeException("Mood log not found"));

		if (!moodLog.getUserId().equals(userId)) {
			throw new RuntimeException("You can only delete your own mood logs");
		}

		// Log the interaction before deleting
		try {
			interactionLogService.logInteraction(
					userId,
					"moodlog",
					"delete",
					"moodlog",
					moodLogId,
					"Menghapus Mood Tracker",
					"Menghapus mood: " + moodLog.getMoodVisual());
		} catch (Exception e) {
			System.err.println("Failed to log moodlog delete interaction: " + e.getMessage());
		}

		moodLogRepository.delete(moodLog);
	}

}