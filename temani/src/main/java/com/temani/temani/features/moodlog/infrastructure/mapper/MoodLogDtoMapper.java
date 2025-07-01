package com.temani.temani.features.moodlog.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.moodlog.domain.model.MoodLog;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;

@Mapper(componentModel = "spring")
public interface MoodLogDtoMapper {

	MoodLogResponse toDto(MoodLog moodLog);

} 