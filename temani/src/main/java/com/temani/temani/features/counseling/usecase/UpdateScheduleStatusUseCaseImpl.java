package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.common.enums.CounselingScheduleStatus;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleEntityMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateScheduleStatusUseCaseImpl implements UpdateScheduleStatusUseCase {

    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleEntityMapper entityMapper;
    private final CounselingScheduleJpaRepository jpaRepository;
    private final InteractionLogService interactionLogService;

    @Override
    public CounselingScheduleResponse execute(UUID scheduleId, UUID peerId, CounselingScheduleStatus status) {
        var scheduleEntity = jpaRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Verify that the requester is the counselor (peer)
        if (!scheduleEntity.getCounselor().getId().equals(peerId)) {
            throw new RuntimeException("Access denied: Only the counselor can update schedule status");
        }

        var oldStatus = scheduleEntity.getStatus();

        // Validate status transitions - allow peer to set status to ONGOING, COMPLETED, or CANCELLED
        // Cannot go back to AVAILABLE or SCHEDULED once it's been booked
        if (status == CounselingScheduleStatus.AVAILABLE && scheduleEntity.getClient() != null) {
            throw new RuntimeException("Cannot set status to AVAILABLE for a booked schedule");
        }

        if (status == CounselingScheduleStatus.SCHEDULED && oldStatus == CounselingScheduleStatus.COMPLETED) {
            throw new RuntimeException("Cannot change status from COMPLETED back to SCHEDULED");
        }

        if (status == CounselingScheduleStatus.AVAILABLE && oldStatus == CounselingScheduleStatus.COMPLETED) {
            throw new RuntimeException("Cannot change status from COMPLETED back to AVAILABLE");
        }

        // Update the status
        scheduleEntity.setStatus(status);

        var saved = jpaRepository.save(scheduleEntity);

        // Use the entity mapper to properly map client and counselor information
        var domain = entityMapper.toDomain(saved);

        // Log interaction if status changed to ONGOING or COMPLETED
        UUID clientId = saved.getClient() != null ? saved.getClient().getId() : null;
        try {
            if (oldStatus != CounselingScheduleStatus.ONGOING && status == CounselingScheduleStatus.ONGOING) {
                // Log for both client and counselor when session starts
                if (clientId != null) {
                    interactionLogService.logInteraction(
                            clientId,
                            "counseling",
                            "start",
                            "counselingschedule",
                            saved.getId(),
                            "Memulai Konsultasi",
                            "Memulai Konsultasi untuk " + saved.getTitle());
                }
                interactionLogService.logInteraction(
                        saved.getCounselor().getId(),
                        "counseling",
                        "start",
                        "counselingschedule",
                        saved.getId(),
                        "Memulai Konsultasi",
                        "Memulai Konsultasi untuk " + saved.getTitle());
            } else if (oldStatus != CounselingScheduleStatus.COMPLETED
                    && status == CounselingScheduleStatus.COMPLETED) {
                // Log for both client and counselor when session completes
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
            }
        } catch (Exception e) {
            System.err.println("Failed to log schedule status change interaction: " + e.getMessage());
        }

        return mapper.toDto(domain);
    }
}

