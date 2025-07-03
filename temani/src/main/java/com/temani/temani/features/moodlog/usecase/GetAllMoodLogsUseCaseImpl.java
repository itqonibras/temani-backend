package com.temani.temani.features.moodlog.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllMoodLogsUseCaseImpl implements GetAllMoodLogsUseCase {

	private final MoodLogRepository moodLogRepository;
	private final MoodLogDtoMapper mapper;

	@Override
	public List<MoodLogResponse> execute(UUID userId) {
		List<com.temani.temani.features.moodlog.domain.model.MoodLog> moodLogs = moodLogRepository.findAllByUserId(userId);
		return moodLogs.stream().map(mapper::toDto).toList();
	}

} 