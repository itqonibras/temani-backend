package com.temani.temani.features.moodlog.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoodLogJpaRepository extends JpaRepository<MoodLogEntity, UUID> {

	List<MoodLogEntity> findAllByUserIdOrderByTimestampDesc(UUID userId);

} 