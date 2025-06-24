package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.infrastructure.persistence.RoleEntity;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

	RoleEntity toEntity(Role domain);

	Role toDomain(RoleEntity entity);

}
