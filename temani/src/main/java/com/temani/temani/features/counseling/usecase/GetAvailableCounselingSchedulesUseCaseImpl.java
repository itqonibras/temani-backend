package com.temani.temani.features.counseling.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.temani.temani.common.enums.CounselingScheduleStatus;
import com.temani.temani.features.counseling.domain.model.CounselingSchedule;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAvailableCounselingSchedulesUseCaseImpl implements GetAvailableCounselingSchedulesUseCase {

    private final CounselingScheduleJpaRepository jpaRepository;
    private final CounselingScheduleDtoMapper mapper;

    @Override
    public List<CounselingScheduleResponse> execute() {
        var availableSchedules = jpaRepository.findByStatusOrderByScheduledAtAsc(CounselingScheduleStatus.AVAILABLE);

        return availableSchedules.stream()
                .map(entity -> {
                    var domain = new CounselingSchedule(entity.getId(),
                            entity.getClient() != null ? entity.getClient().getId() : null,
                            entity.getCounselor().getId(),
                            entity.getCounselorName(),
                            entity.getScheduledAt(),
                            entity.getTitle(), entity.getDescription(), entity.getMeetingLink(), entity.getNotes(),
                            entity.getStatus());
                    return mapper.toDto(domain);
                })
                .toList();
    }
}
