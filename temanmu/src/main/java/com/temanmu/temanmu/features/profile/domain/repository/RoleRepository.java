package com.temanmu.temanmu.features.profile.domain.repository;

import java.util.Optional;

import com.temanmu.temanmu.features.profile.domain.model.Role;

public interface RoleRepository {

	Role save(Role role);

	Optional<Role> findByName(String name);

}
