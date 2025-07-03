package com.temani.temani.features.moodlog.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.infrastructure.mapper.MoodLogDtoMapper;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogEntity;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogJpaRepository;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temani.temani.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMoodLogUseCaseImpl implements CreateMoodLogUseCase {

	private final MoodLogRepository moodLogRepository;
	private final MoodLogDtoMapper mapper;
	private final MoodLogJpaRepository moodLogJpaRepository;
	private final UserJpaRepository userJpaRepository;

	@Override
	public MoodLogResponse execute(MoodLogRequest request, UUID userId) {
		// Get the user entity
		var userEntity = userJpaRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("User not found"));
		
		// Create the mood log entity directly
		MoodLogEntity moodLogEntity = new MoodLogEntity();
		moodLogEntity.setUser(userEntity);
		moodLogEntity.setMoodVisual(request.getMoodVisual());
		moodLogEntity.setEmotionScale(request.getEmotionScale());
		moodLogEntity.setTimestamp(LocalDateTime.now());
		
		MoodLogEntity savedEntity = moodLogJpaRepository.save(moodLogEntity);
		
		// Convert to domain model and then to DTO
		MoodLog moodLog = new MoodLog(
			savedEntity.getId(),
			savedEntity.getUser().getId(),
			savedEntity.getMoodVisual(),
			savedEntity.getEmotionScale(),
			savedEntity.getTimestamp()
		);
		
		return mapper.toDto(moodLog);
	}

} 