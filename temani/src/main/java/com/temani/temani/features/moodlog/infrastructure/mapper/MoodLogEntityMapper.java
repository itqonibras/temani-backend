package com.temani.temani.features.moodlog.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.infrastructure.persistence.MoodLogEntity;

@Mapper(componentModel = "spring")
public interface MoodLogEntityMapper {

	@Mapping(source = "user.id", target = "userId")
	MoodLog toDomain(MoodLogEntity entity);

	@Mapping(target = "user", ignore = true)
	MoodLogEntity toEntity(MoodLog domain);

} 