package com.temanmu.temanmu.features.journal.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.journal.domain.repository.JournalRepository;
import com.temanmu.temanmu.features.journal.infrastructure.mapper.JournalDtoMapper;
import com.temanmu.temanmu.features.journal.presentation.dto.response.JournalResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllJournalUseCaseImpl implements GetAllJournalsUseCase {

	private final JournalRepository journalRepository;

	private final JournalDtoMapper mapper;

	@Override
	public List<JournalResponse> execute(UUID userId) {
		return journalRepository.findAllByUserId(userId).stream().map(mapper::toDto).toList();
	}

}
