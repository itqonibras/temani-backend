package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.profile.domain.model.ClientProfile;
import com.temani.temani.features.profile.presentation.dto.response.ClientProfileResponse;

@Mapper(componentModel = "spring")
public interface ClientProfileDtoMapper {

	ClientProfileResponse toDto(ClientProfile domain);

}
