package com.temanmu.temanmu.features.profile.infrastructure.persistence;

import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.features.profile.domain.model.PeerProfile;
import com.temanmu.temanmu.features.profile.domain.repository.PeerProfileRepository;
import com.temanmu.temanmu.features.profile.infrastructure.mapper.PeerProfileEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PeerProfileRepositoryImpl implements PeerProfileRepository {

	private final PeerProfileJpaRepository jpa;

	private final PeerProfileEntityMapper mapper;

	@Override
	public PeerProfile save(PeerProfile peerProfile) {
		PeerProfileEntity savedEntity = jpa.save(mapper.toEntity(peerProfile));
		return mapper.toDomain(savedEntity);
	}

}
