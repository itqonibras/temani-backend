package com.temani.temani.features.journal.usecase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.domain.repository.JournalRepository;
import com.temani.temani.features.journal.infrastructure.mapper.JournalDtoMapper;
import com.temani.temani.features.journal.presentation.dto.request.JournalRequest;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateJournalUseCaseImpl implements CreateJournalUseCase {

	private final JournalRepository journalRepository;

	private final JournalDtoMapper mapper;
	private final InteractionLogService interactionLogService;

	@Override
	public JournalResponse execute(JournalRequest request, UUID userId) {
		Journal journal = new Journal(null, userId, request.getTitle(), request.getContent(), null, null);
		Journal savedJournal = journalRepository.save(journal);

		// Log the interaction
		try {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			String formattedDate = now.format(formatter);

			interactionLogService.logInteraction(
					userId,
					"journal",
					"create",
					"journal",
					savedJournal.getId(),
					"Menulis Journal",
					"Menulis tentang " + formattedDate);
		} catch (Exception e) {
			System.err.println("Failed to log journal interaction: " + e.getMessage());
		}

		return mapper.toDto(savedJournal);
	}

}
