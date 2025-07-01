package com.temani.temani.features.moodlog.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.moodlog.domain.model.MoodLog;

public interface MoodLogRepository {

	MoodLog save(MoodLog moodLog);

	void delete(MoodLog moodLog);

	Optional<MoodLog> findById(UUID id);

	List<MoodLog> findAllByUserId(UUID userId);

} 