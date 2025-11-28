package com.temanmu.temanmu.features.journal.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.journal.presentation.dto.request.JournalRequest;
import com.temanmu.temanmu.features.journal.presentation.dto.response.JournalResponse;

public interface CreateJournalUseCase {

	JournalResponse execute(JournalRequest request, UUID userId);

}
