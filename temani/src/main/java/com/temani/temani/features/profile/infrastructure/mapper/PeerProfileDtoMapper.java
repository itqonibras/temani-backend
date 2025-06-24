package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.profile.domain.model.PeerProfile;
import com.temani.temani.features.profile.presentation.dto.response.PeerProfileResponse;

@Mapper(componentModel = "spring")
public interface PeerProfileDtoMapper {

	PeerProfileResponse toDto(PeerProfile domain);

}
