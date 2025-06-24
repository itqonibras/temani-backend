package com.temani.temani.features.profile.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.repository.RoleRepository;
import com.temani.temani.features.profile.infrastructure.mapper.RoleEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

	private final RoleJpaRepository jpa;

	private final RoleEntityMapper mapper;

	@Override
	public Role save(Role role) {
		RoleEntity entity = mapper.toEntity(role);
		RoleEntity saved = jpa.save(entity);
		return mapper.toDomain(saved);
	}

	@Override
	public Optional<Role> findByName(String name) {
		return jpa.findByName(name).map(mapper::toDomain);
	}

}
