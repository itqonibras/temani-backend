package com.temani.temani.features.journal.infrastructure.persistance;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalJpaRepository extends JpaRepository<JournalEntity, UUID> {
    List<JournalEntity> findAllByUserId(UUID userId);
}
