package com.temani.temani.features.journal.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;

public interface GetAllJournalsUseCase {
    List<JournalResponse> execute(UUID userId);
}
