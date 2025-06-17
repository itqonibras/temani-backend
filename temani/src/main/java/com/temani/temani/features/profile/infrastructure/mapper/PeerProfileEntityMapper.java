package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.profile.domain.model.PeerProfile;
import com.temani.temani.features.profile.infrastructure.persistence.PeerProfileEntity;

@Mapper(componentModel = "spring")
public interface PeerProfileEntityMapper {
    @Mapping(target = "user", ignore = true)
    PeerProfileEntity toEntity(PeerProfile domain);

    PeerProfile toDomain(PeerProfileEntity entity);
}
