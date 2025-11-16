package com.temani.temani.features.counseling.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetClientCounselingSchedulesUseCaseImpl implements GetClientCounselingSchedulesUseCase {

    private final CounselingScheduleRepository repository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public List<CounselingScheduleResponse> execute(UUID clientId) {
        LocalDateTime now = LocalDateTime.now();
        return repository.findAllByClientId(clientId).stream()
                .filter(schedule -> schedule.getScheduledAt() != null && !schedule.getScheduledAt().isBefore(now))
                .map(mapper::toDto)
                .toList();
    }
}
