package com.temani.temani.features.journal.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.common.constants.JournalMessages;
import com.temani.temani.features.journal.domain.model.Journal;
import com.temani.temani.features.journal.domain.repository.JournalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteJournalUseCaseImpl implements DeleteJournalUseCase {

	private final JournalRepository journalRepository;

	@Override
	public void execute(UUID id, UUID userId) {
		Journal journal = journalRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException(JournalMessages.JOURNAL_NOT_FOUND));

		if (!journal.getUserId().equals(userId)) {
			throw new IllegalStateException(JournalMessages.NOT_ALLOWED_DELETE_JOURNAL);
		}

		journalRepository.delete(journal);
	}

}
