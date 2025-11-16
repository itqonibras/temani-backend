package com.temani.temani.features.counseling.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.temani.temani.common.enums.CounselingScheduleStatus;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleEntityMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAvailableCounselingSchedulesUseCaseImpl implements GetAvailableCounselingSchedulesUseCase {

    private final CounselingScheduleJpaRepository jpaRepository;
    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleEntityMapper entityMapper;

    @Override
    public List<CounselingScheduleResponse> execute() {
        var availableSchedules = jpaRepository.findByStatusOrderByScheduledAtAsc(CounselingScheduleStatus.AVAILABLE);

        LocalDateTime now = LocalDateTime.now();

        return availableSchedules.stream()
                .map(entity -> {
                    // Use the entity mapper to properly map client and counselor information
                    var domain = entityMapper.toDomain(entity);
                    return mapper.toDto(domain);
                })
                .filter(dto -> dto.getScheduledAt() != null && !dto.getScheduledAt().isBefore(now))
                .toList();
    }
}
