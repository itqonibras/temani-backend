package com.temanmu.temanmu.features.profile.infrastructure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.profile.domain.repository.RoleRepository;
import com.temanmu.temanmu.features.profile.infrastructure.mapper.RoleEntityMapper;

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
