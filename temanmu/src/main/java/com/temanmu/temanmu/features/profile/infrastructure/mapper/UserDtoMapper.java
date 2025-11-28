package com.temanmu.temanmu.features.profile.infrastructure.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

	@Mapping(source = "roles", target = "roles")
	UserResponse toDto(User domain);

	default Set<String> map(Set<Role> roles) {
		return roles.stream().map(Role::getName).collect(Collectors.toSet());
	}

}
