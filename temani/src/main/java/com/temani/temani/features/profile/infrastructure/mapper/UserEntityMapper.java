package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity toEntity(User user);
    User toDomain(UserEntity entity);
}
