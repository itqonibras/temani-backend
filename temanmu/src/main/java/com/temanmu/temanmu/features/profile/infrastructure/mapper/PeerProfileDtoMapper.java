package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.profile.domain.model.PeerProfile;
import com.temanmu.temanmu.features.profile.presentation.dto.response.PeerProfileResponse;

@Mapper(componentModel = "spring")
public interface PeerProfileDtoMapper {

	PeerProfileResponse toDto(PeerProfile domain);

}
