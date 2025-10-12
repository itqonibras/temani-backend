package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleEntity;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCounselingScheduleUseCaseImpl implements UpdateCounselingScheduleUseCase {

    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public CounselingScheduleResponse execute(UUID scheduleId, UUID requesterId, CounselingScheduleRequest request) {
        var existing = jpaRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        if (!existing.getClient().getId().equals(requesterId) && !existing.getCounselor().getId().equals(requesterId)) {
            throw new RuntimeException("Access denied");
        }

        var counselor = userJpaRepository.findById(request.getCounselorId())
                .orElseThrow(() -> new RuntimeException("Counselor not found"));
        boolean counselorIsPeer = counselor.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
        if (!counselorIsPeer) {
            throw new RuntimeException("Counselor must have PEER role");
        }

        existing.setCounselor(counselor);
        existing.setCounselorName(counselor.getName());
        existing.setScheduledAt(request.getScheduledAt());
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setMeetingLink(request.getMeetingLink());
        existing.setNotes(request.getNotes());
        if (existing.getStatus() == null) {
            existing.setStatus(com.temani.temani.common.enums.CounselingScheduleStatus.SCHEDULED);
        }

        CounselingScheduleEntity saved = jpaRepository.save(existing);
        var domain = new com.temani.temani.features.counseling.domain.model.CounselingSchedule(
                saved.getId(), saved.getClient().getId(), saved.getCounselor().getId(), counselor.getName(),
                saved.getScheduledAt(),
                saved.getTitle(), saved.getDescription(), saved.getMeetingLink(), saved.getNotes(), saved.getStatus());
        return mapper.toDto(domain);
    }
}
