package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.profile.domain.model.CaregiverProfile;
import com.temanmu.temanmu.features.profile.presentation.dto.response.CaregiverProfileResponse;

@Mapper(componentModel = "spring")
public interface CaregiverProfileDtoMapper {

	CaregiverProfileResponse toDto(CaregiverProfile domain);

}
