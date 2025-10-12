package com.temani.temani.features.counseling.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.counseling.domain.model.CounselingSchedule;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

@Mapper(componentModel = "spring")
public interface CounselingScheduleDtoMapper {

    CounselingScheduleResponse toDto(CounselingSchedule schedule);
}
