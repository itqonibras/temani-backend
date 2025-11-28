package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.profile.domain.model.ClientProfile;
import com.temanmu.temanmu.features.profile.infrastructure.persistence.ClientProfileEntity;

@Mapper(componentModel = "spring")
public interface ClientProfileEntityMapper {

	@Mapping(target = "user", ignore = true)
	ClientProfileEntity toEntity(ClientProfile domain);

	ClientProfile toDomain(ClientProfileEntity entity);

}
