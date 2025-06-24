package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.profile.domain.model.CaregiverProfile;
import com.temani.temani.features.profile.infrastructure.persistence.CaregiverProfileEntity;

@Mapper(componentModel = "spring")
public interface CaregiverProfileEntityMapper {

	@Mapping(target = "user", ignore = true)
	CaregiverProfileEntity toEntity(CaregiverProfile domain);

	CaregiverProfile toDomain(CaregiverProfileEntity entity);

}
