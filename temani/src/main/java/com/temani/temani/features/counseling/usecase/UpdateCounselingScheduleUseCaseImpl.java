package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleEntityMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleEntity;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateCounselingScheduleUseCaseImpl implements UpdateCounselingScheduleUseCase {

    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleEntityMapper entityMapper;
    private final CounselingScheduleJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final InteractionLogService interactionLogService;

    @Override
    public CounselingScheduleResponse execute(UUID scheduleId, UUID requesterId, CounselingScheduleRequest request) {
        var existing = jpaRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Fix: Handle null client case for PEER-created schedules
        boolean isClient = existing.getClient() != null && existing.getClient().getId().equals(requesterId);
        boolean isCounselor = existing.getCounselor().getId().equals(requesterId);

        if (!isClient && !isCounselor) {
            throw new RuntimeException("Access denied");
        }

        var counselor = userJpaRepository.findById(request.getCounselorId())
                .orElseThrow(() -> new RuntimeException("Counselor not found"));
        boolean counselorIsPeer = counselor.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
        if (!counselorIsPeer) {
            throw new RuntimeException("Counselor must have PEER role");
        }

        // Store the old status to detect changes
        var oldStatus = existing.getStatus();

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

        // Fix: Handle null client case when creating domain object
        UUID clientId = saved.getClient() != null ? saved.getClient().getId() : null;

        // Log interaction if status changed to COMPLETED
        if (oldStatus != com.temani.temani.common.enums.CounselingScheduleStatus.COMPLETED &&
                saved.getStatus() == com.temani.temani.common.enums.CounselingScheduleStatus.COMPLETED) {
            try {
                // Log for both client and counselor
                if (clientId != null) {
                    interactionLogService.logInteraction(
                            clientId,
                            "counseling",
                            "complete",
                            "counselingschedule",
                            saved.getId(),
                            "Melaksanakan Konsultasi",
                            "Melaksanakan Konsultasi untuk " + saved.getTitle());
                }
                interactionLogService.logInteraction(
                        saved.getCounselor().getId(),
                        "counseling",
                        "complete",
                        "counselingschedule",
                        saved.getId(),
                        "Melaksanakan Konsultasi",
                        "Melaksanakan Konsultasi untuk " + saved.getTitle());
            } catch (Exception e) {
                System.err.println("Failed to log counseling completion interaction: " + e.getMessage());
            }
        }

        // Use the entity mapper to properly map client and counselor information
        var domain = entityMapper.toDomain(saved);
        return mapper.toDto(domain);
    }
}
