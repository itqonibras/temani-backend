package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.profile.domain.model.CaregiverProfile;
import com.temani.temani.features.profile.presentation.dto.response.CaregiverProfileResponse;

@Mapper(componentModel = "spring")
public interface CaregiverProfileDtoMapper {

	CaregiverProfileResponse toDto(CaregiverProfile domain);

}
