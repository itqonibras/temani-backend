package com.temani.temani.features.journal.infrastructure.persistance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.domain.repository.JournalRepository;
import com.temani.temani.features.journal.infrastructure.mapper.JournalEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JournalRepositoryImpl implements JournalRepository {

	private final JournalJpaRepository jpa;

	private final JournalEntityMapper mapper;

	@Override
	public Journal save(Journal journal) {
		JournalEntity savedEntity = jpa.save(mapper.toEntity(journal));
		return mapper.toDomain(savedEntity);
	}

	@Override
	public void delete(Journal journal) {
		jpa.delete(mapper.toEntity(journal));
	}

	@Override
	public Optional<Journal> findById(UUID id) {
		return jpa.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<Journal> findAllByUserId(UUID userId) {
		List<JournalEntity> entities = jpa.findAllByUserId(userId);
		return entities.stream().map(mapper::toDomain).toList();
	}

}
