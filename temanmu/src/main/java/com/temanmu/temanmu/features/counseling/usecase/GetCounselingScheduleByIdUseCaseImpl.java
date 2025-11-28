package com.temanmu.temanmu.features.counseling.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temanmu.temanmu.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCounselingScheduleByIdUseCaseImpl implements GetCounselingScheduleByIdUseCase {

    private final CounselingScheduleRepository repository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public CounselingScheduleResponse execute(UUID id, UUID requesterId) {
        var schedule = repository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Check if schedule is in the past - throw exception if it is
        LocalDateTime now = LocalDateTime.now();
        if (schedule.getScheduledAt() != null && schedule.getScheduledAt().isBefore(now)) {
            throw new RuntimeException("Schedule not found");
        }

        // Visibility enforcement - allow access if user is the client or counselor
        // Handle null clientId for AVAILABLE schedules
        boolean isClient = schedule.getClientId() != null && schedule.getClientId().equals(requesterId);
        boolean isCounselor = schedule.getCounselorId().equals(requesterId);
        if (!isClient && !isCounselor) {
            throw new RuntimeException("Access denied");
        }
        return mapper.toDto(schedule);
    }
}
