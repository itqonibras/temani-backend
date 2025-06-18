package com.temani.temani.features.journal.usecase;

import java.util.UUID;

import com.temani.temani.features.journal.presentation.dto.request.JournalRequest;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;

public interface CreateJournalUseCase {
    JournalResponse execute(JournalRequest request, UUID userId);
}
