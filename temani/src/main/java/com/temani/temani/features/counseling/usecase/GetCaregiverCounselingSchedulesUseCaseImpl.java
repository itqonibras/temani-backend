package com.temani.temani.features.counseling.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.domain.repository.CounselingScheduleRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetCaregiverCounselingSchedulesUseCaseImpl implements GetCaregiverCounselingSchedulesUseCase {

    private final CounselingScheduleRepository repository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public List<CounselingScheduleResponse> execute(UUID caregiverId) {
        return repository.findAllByCounselorId(caregiverId).stream().map(mapper::toDto).toList();
    }
}
