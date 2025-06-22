package com.temani.temani.features.journal.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.journal.domain.repository.JournalRepository;
import com.temani.temani.features.journal.infrastructure.mapper.JournalDtoMapper;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllJournalUseCaseImpl implements GetAllJournalsUseCase{
    
    private final JournalRepository journalRepository;
    private final JournalDtoMapper mapper;

    @Override
    public List<JournalResponse> execute(UUID userId) {
        return journalRepository.findAllByUserId(userId)
        .stream()
        .map(mapper::toDto)
        .toList();
    }
}
