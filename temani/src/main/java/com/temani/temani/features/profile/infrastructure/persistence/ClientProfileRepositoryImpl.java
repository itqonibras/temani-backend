package com.temani.temani.features.profile.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.profile.domain.model.ClientProfile;
import com.temani.temani.features.profile.domain.repository.ClientProfileRepository;
import com.temani.temani.features.profile.infrastructure.mapper.ClientProfileEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ClientProfileRepositoryImpl implements ClientProfileRepository {

	private final ClientProfileJpaRepository jpa;

	private final ClientProfileEntityMapper mapper;

	@Override
	public ClientProfile save(ClientProfile clientProfile) {
		ClientProfileEntity savedEntity = jpa.save(mapper.toEntity(clientProfile));
		return mapper.toDomain(savedEntity);
	}

}
