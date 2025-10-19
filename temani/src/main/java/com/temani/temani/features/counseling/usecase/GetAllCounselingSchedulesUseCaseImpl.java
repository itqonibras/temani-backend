package com.temani.temani.features.counseling.usecase;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temani.temani.common.enums.CounselingScheduleStatus;
import com.temani.temani.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

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

        // Filter by status if provided
        if (status != null && !status.isEmpty()) {
            schedules = schedules.stream()
                    .filter(schedule -> status.contains(schedule.getStatus()))
                    .collect(Collectors.toList());
        }

        return schedules.stream().map(mapper::toDto).toList();
    }
}
