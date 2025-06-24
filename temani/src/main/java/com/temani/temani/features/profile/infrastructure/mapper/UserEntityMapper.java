package com.temani.temani.features.profile.infrastructure.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;

@Mapper(componentModel = "spring",
		uses = { ClientProfileEntityMapper.class, CaregiverProfileEntityMapper.class, PeerProfileEntityMapper.class })
public interface UserEntityMapper {

	@Mapping(source = "clientProfile", target = "clientProfileEntity")
	@Mapping(source = "caregiverProfile", target = "caregiverProfileEntity")
	@Mapping(source = "peerProfile", target = "peerProfileEntity")
	UserEntity toEntity(User domain);

	@Mapping(source = "clientProfileEntity", target = "clientProfile")
	@Mapping(source = "caregiverProfileEntity", target = "caregiverProfile")
	@Mapping(source = "peerProfileEntity", target = "peerProfile")
	User toDomain(UserEntity entity);

	@AfterMapping
	default void linkProfiles(@MappingTarget UserEntity userEntity, User domain) {
		if (userEntity.getClientProfileEntity() != null) {
			userEntity.getClientProfileEntity().setUser(userEntity);
		}
		if (userEntity.getCaregiverProfileEntity() != null) {
			userEntity.getCaregiverProfileEntity().setUser(userEntity);
		}
		if (userEntity.getPeerProfileEntity() != null) {
			userEntity.getPeerProfileEntity().setUser(userEntity);
		}
	}

}
