package com.temanmu.temanmu.features.counseling.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.counseling.domain.model.CounselingSchedule;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

@Mapper(componentModel = "spring")
public interface CounselingScheduleDtoMapper {

    CounselingScheduleResponse toDto(CounselingSchedule schedule);
}
