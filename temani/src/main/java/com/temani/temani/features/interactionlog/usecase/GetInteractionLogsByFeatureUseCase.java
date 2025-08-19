package com.temani.temani.features.interactionlog.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.interactionlog.presentation.dto.response.InteractionLogResponse;

public interface GetInteractionLogsByFeatureUseCase {
    List<InteractionLogResponse> execute(UUID userId, String feature);
}