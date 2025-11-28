package com.temanmu.temanmu.features.interactionlog.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.interactionlog.presentation.dto.response.InteractionLogResponse;

public interface GetAllInteractionLogsUseCase {
    List<InteractionLogResponse> execute(UUID userId);
}