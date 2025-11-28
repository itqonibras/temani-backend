package com.temanmu.temanmu.features.journal.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.journal.presentation.dto.response.JournalResponse;

public interface GetAllJournalsUseCase {

	List<JournalResponse> execute(UUID userId);

}
