package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCounselingScheduleByIdUseCaseImpl implements GetCounselingScheduleByIdUseCase {

    private final CounselingScheduleRepository repository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public CounselingScheduleResponse execute(UUID id, UUID requesterId) {
        var schedule = repository.findById(id).orElseThrow(() -> new RuntimeException("Schedule not found"));
        // Visibility enforcement can be enhanced as needed
        if (!schedule.getClientId().equals(requesterId) && !schedule.getCounselorId().equals(requesterId)) {
            throw new RuntimeException("Access denied");
        }
        return mapper.toDto(schedule);
    }
}
