package com.temanmu.temanmu.features.counseling.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.enums.CounselingScheduleStatus;
import com.temanmu.temanmu.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temanmu.temanmu.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllCounselingSchedulesUseCaseImpl implements GetAllCounselingSchedulesUseCase {

    private final CounselingScheduleRepository repository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public List<CounselingScheduleResponse> execute(UUID userId, boolean isCaregiver,
            List<CounselingScheduleStatus> status) {

        var schedules = isCaregiver ? repository.findAllByCounselorId(userId) : repository.findAllByClientId(userId);

        LocalDateTime now = LocalDateTime.now();

        // Filter out past schedules (scheduledAt must be in the future or now)
        schedules = schedules.stream()
                .filter(schedule -> schedule.getScheduledAt() != null && !schedule.getScheduledAt().isBefore(now))
                .collect(Collectors.toList());

        // Filter by status if provided
        if (status != null && !status.isEmpty()) {
            schedules = schedules.stream()
                    .filter(schedule -> status.contains(schedule.getStatus()))
                    .collect(Collectors.toList());
        }

        return schedules.stream().map(mapper::toDto).toList();
    }
}
