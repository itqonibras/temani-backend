package com.temanmu.temanmu.features.profile.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.features.profile.domain.model.CaregiverProfile;
import com.temanmu.temanmu.features.profile.domain.repository.CaregiverProfileRepository;
import com.temanmu.temanmu.features.profile.infrastructure.mapper.CaregiverProfileEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CaregiverProfileRepositoryImpl implements CaregiverProfileRepository {

	private final CaregiverProfileJpaRepository jpa;

	private final CaregiverProfileEntityMapper mapper;

	@Override
	public CaregiverProfile save(CaregiverProfile caregiverProfile) {
		CaregiverProfileEntity savedEntity = jpa.save(mapper.toEntity(caregiverProfile));
		return mapper.toDomain(savedEntity);
	}

}
