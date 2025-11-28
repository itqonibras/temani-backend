package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.profile.domain.model.ClientProfile;
import com.temanmu.temanmu.features.profile.presentation.dto.response.ClientProfileResponse;

@Mapper(componentModel = "spring")
public interface ClientProfileDtoMapper {

	ClientProfileResponse toDto(ClientProfile domain);

}
