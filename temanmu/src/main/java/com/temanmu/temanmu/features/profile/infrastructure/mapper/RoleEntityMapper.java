package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.profile.infrastructure.persistence.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

	RoleEntity toEntity(Role domain);

	Role toDomain(RoleEntity entity);

}
