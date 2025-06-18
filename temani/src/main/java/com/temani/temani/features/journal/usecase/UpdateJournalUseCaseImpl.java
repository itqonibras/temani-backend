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
public class UpdateJournalUseCaseImpl implements UpdateJournalUseCase{

    private final JournalRepository journalRepository;
    private final JournalDtoMapper mapper;

    @Override
    public JournalResponse execute(JournalRequest request, UUID id, UUID userId) {
        Journal existingJournal = journalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Journal not found!"));

        if (!existingJournal.getUserId().equals(userId)) {
            throw new IllegalStateException("You're not allowed to edit this journal!");
        }

        Journal updatedJournal = new Journal(
                existingJournal.getId(),
                existingJournal.getUserId(),
                request.getTitle(),
                request.getContent(),
                existingJournal.getCreatedAt(), 
                existingJournal.getUpdatedAt());
        
        Journal savedJournal = journalRepository.save(updatedJournal);
        return mapper.toDto(savedJournal);
    }

}
