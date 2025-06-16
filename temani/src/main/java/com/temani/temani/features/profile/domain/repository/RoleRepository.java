package com.temani.temani.features.profile.domain.repository;

import java.util.Optional;

import com.temani.temani.features.profile.domain.model.Role;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
