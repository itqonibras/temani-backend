package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.profile.domain.model.ClientProfile;
import com.temani.temani.features.profile.infrastructure.persistence.ClientProfileEntity;

@Mapper(componentModel = "spring")
public interface ClientProfileEntityMapper {
    @Mapping(target = "user", ignore = true)
    ClientProfileEntity toEntity(ClientProfile domain);

    ClientProfile toDomain(ClientProfileEntity entity);
}
