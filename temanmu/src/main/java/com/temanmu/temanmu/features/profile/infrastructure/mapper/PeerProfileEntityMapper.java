package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.profile.domain.model.PeerProfile;
import com.temanmu.temanmu.features.profile.infrastructure.persistence.PeerProfileEntity;

@Mapper(componentModel = "spring")
public interface PeerProfileEntityMapper {

	@Mapping(target = "user", ignore = true)
	PeerProfileEntity toEntity(PeerProfile domain);

	PeerProfile toDomain(PeerProfileEntity entity);

}
