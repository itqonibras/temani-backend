package com.temani.temani.features.journal.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.journal.domain.model.Journal;

public interface JournalRepository {
    Journal save(Journal journal);
    void delete(Journal journal);
    Optional<Journal> findById(UUID id);
    List<Journal> findAllByUserId(UUID userId);
}
