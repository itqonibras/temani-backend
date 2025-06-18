package com.temani.temani.features.journal.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.domain.repository.JournalRepository;
import com.temani.temani.features.journal.infrastructure.mapper.JournalDtoMapper;
import com.temani.temani.features.journal.presentation.dto.request.JournalRequest;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateJournalUseCaseImpl implements CreateJournalUseCase {

    private final JournalRepository journalRepository;
    private final JournalDtoMapper mapper;
    
    @Override
    public JournalResponse execute(JournalRequest request, UUID userId) {
        Journal journal = new Journal(
            null,
            userId,
            request.getTitle(),
            request.getContent(),
            null,
            null);
        Journal savedJournal = journalRepository.save(journal);
        return mapper.toDto(savedJournal);
    }

}
