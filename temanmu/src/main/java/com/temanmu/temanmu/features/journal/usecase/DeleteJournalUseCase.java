package com.temanmu.temanmu.features.journal.usecase;

import java.util.UUID;

public interface DeleteJournalUseCase {

	void execute(UUID id, UUID userId);

}
