package com.temanmu.temanmu.features.interactionlog.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.interactionlog.domain.repository.InteractionLogRepository;
import com.temanmu.temanmu.features.interactionlog.infrastructure.mapper.InteractionLogDtoMapper;
import com.temanmu.temanmu.features.interactionlog.presentation.dto.response.InteractionLogResponse;

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