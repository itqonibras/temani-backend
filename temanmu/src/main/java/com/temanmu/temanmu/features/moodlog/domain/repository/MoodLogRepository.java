package com.temanmu.temanmu.features.moodlog.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temanmu.temanmu.features.moodlog.domain.model.MoodLog;

public interface MoodLogRepository {

	MoodLog save(MoodLog moodLog);

	void delete(MoodLog moodLog);

	Optional<MoodLog> findById(UUID id);

	List<MoodLog> findAllByUserId(UUID userId);

	List<MoodLog> findAllByUserIdAndTimestampBetween(UUID userId, LocalDateTime start, LocalDateTime end);

}