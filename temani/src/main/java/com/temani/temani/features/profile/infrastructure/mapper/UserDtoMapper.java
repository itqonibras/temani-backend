package com.temani.temani.features.profile.infrastructure.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.profile.domain.model.CaregiverProfile;
import com.temani.temani.features.profile.domain.model.ClientProfile;
import com.temani.temani.features.profile.domain.model.PeerProfile;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.presentation.dto.response.CaregiverProfileResponse;
import com.temani.temani.features.profile.presentation.dto.response.ClientProfileResponse;
import com.temani.temani.features.profile.presentation.dto.response.PeerProfileResponse;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    @Mapping(source = "roles", target = "roles")
    UserResponse toDto(User domain);

    default Set<String> map(Set<Role> roles) {
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }

    ClientProfileResponse toDto(ClientProfile domain);
    
    CaregiverProfileResponse toDto(CaregiverProfile domain);

    PeerProfileResponse toDto(PeerProfile domain);

}
