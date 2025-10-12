package com.temani.temani.features.counseling.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.counseling.domain.model.CounselingSchedule;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleEntity;

@Mapper(componentModel = "spring")
public interface CounselingScheduleEntityMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "counselor.id", target = "counselorId")
    @Mapping(source = "counselor.name", target = "counselorName")
    @Mapping(source = "status", target = "status")
    CounselingSchedule toDomain(CounselingScheduleEntity entity);

    @Mapping(target = "client", ignore = true)
    @Mapping(target = "counselor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CounselingScheduleEntity toEntity(CounselingSchedule domain);
}
