package com.temanmu.temanmu.features.moodlog.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.moodlog.domain.repository.MoodLogRepository;
import com.temanmu.temanmu.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllMoodLogsUseCaseImpl implements GetAllMoodLogsUseCase {

	private final MoodLogRepository moodLogRepository;
	private final MoodLogDtoMapper mapper;

	@Override
	public List<MoodLogResponse> execute(UUID userId) {
		List<com.temanmu.temanmu.features.moodlog.domain.model.MoodLog> moodLogs = moodLogRepository.findAllByUserId(userId);
		return moodLogs.stream().map(mapper::toDto).toList();
	}

}