package com.temani.temani.features.interactionlog.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.interactionlog.domain.repository.InteractionLogRepository;
import com.temani.temani.features.interactionlog.infrastructure.mapper.InteractionLogDtoMapper;
import com.temani.temani.features.interactionlog.presentation.dto.response.InteractionLogResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetInteractionLogsByFeatureUseCaseImpl implements GetInteractionLogsByFeatureUseCase {

    private final InteractionLogRepository interactionLogRepository;
    private final InteractionLogDtoMapper mapper;

    @Override
    public List<InteractionLogResponse> execute(UUID userId, String feature) {
        List<InteractionLogResponse> interactionLogs = interactionLogRepository
                .findAllByUserIdAndFeature(userId, feature)
                .stream()
                .map(mapper::toDto)
                .toList();
        return interactionLogs;
    }
}