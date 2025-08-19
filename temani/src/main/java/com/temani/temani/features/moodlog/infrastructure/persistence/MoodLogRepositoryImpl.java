package com.temani.temani.features.moodlog.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.domain.repository.MoodLogRepository;
import com.temani.temani.features.moodlog.infrastructure.mapper.MoodLogEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MoodLogRepositoryImpl implements MoodLogRepository {

	private final MoodLogJpaRepository jpaRepository;
	private final MoodLogEntityMapper mapper;

	@Override
	public MoodLog save(MoodLog moodLog) {
		MoodLogEntity entity = mapper.toEntity(moodLog);
		// We need to set the user entity properly
		// This will be handled in the use case by setting the user entity
		MoodLogEntity savedEntity = jpaRepository.save(entity);
		return mapper.toDomain(savedEntity);
	}

	@Override
	public void delete(MoodLog moodLog) {
		MoodLogEntity entity = mapper.toEntity(moodLog);
		jpaRepository.delete(entity);
	}

	@Override
	public Optional<MoodLog> findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<MoodLog> findAllByUserId(UUID userId) {
		List<MoodLogEntity> entities = jpaRepository.findAllByUserIdOrderByTimestampDesc(userId);
		return entities.stream().map(mapper::toDomain).toList();
	}

	@Override
	public List<MoodLog> findAllByUserIdAndTimestampBetween(UUID userId, LocalDateTime start, LocalDateTime end) {
		List<MoodLogEntity> entities = jpaRepository
				.findAllByUserIdAndTimestampBetweenOrderByTimestampDesc(userId, start, end);
		return entities.stream().map(mapper::toDomain).toList();
	}

}