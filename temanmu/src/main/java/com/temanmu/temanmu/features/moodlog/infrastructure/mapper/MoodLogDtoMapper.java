package com.temanmu.temanmu.features.moodlog.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.moodlog.domain.model.MoodLog;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;

@Mapper(componentModel = "spring")
public interface MoodLogDtoMapper {

	MoodLogResponse toDto(MoodLog moodLog);

}