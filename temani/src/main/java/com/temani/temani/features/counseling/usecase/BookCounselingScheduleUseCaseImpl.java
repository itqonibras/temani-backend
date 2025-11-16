package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleEntityMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookCounselingScheduleUseCaseImpl implements BookCounselingScheduleUseCase {

    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleEntityMapper entityMapper;
    private final CounselingScheduleJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final InteractionLogService interactionLogService;

    @Override
    public CounselingScheduleResponse execute(UUID scheduleId, UUID clientId) {
        // Find the schedule
        var scheduleEntity = jpaRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Verify the schedule is available for booking
        if (scheduleEntity.getStatus() != com.temani.temani.common.enums.CounselingScheduleStatus.AVAILABLE) {
            throw new RuntimeException("Schedule is not available for booking");
        }

        // Check if already has a client
        if (scheduleEntity.getClient() != null) {
            throw new RuntimeException("Schedule is already booked");
        }

        // Find the client
        var client = userJpaRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Verify client has CLIENT role
        boolean clientIsClient = client.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("CLIENT"));
        if (!clientIsClient) {
            throw new RuntimeException("User must have CLIENT role to book schedules");
        }

        // Book the schedule - assign client but keep status as AVAILABLE until payment
        scheduleEntity.setClient(client);
        scheduleEntity.setStatus(com.temani.temani.common.enums.CounselingScheduleStatus.AVAILABLE);

        var saved = jpaRepository.save(scheduleEntity);
        // Use the entity mapper to properly map client and counselor information
        var domain = entityMapper.toDomain(saved);

        // Log the interaction
        try {
            interactionLogService.logInteraction(
                    clientId,
                    "counseling",
                    "book",
                    "counselingschedule",
                    saved.getId(),
                    "Mendaftar Konsultasi",
                    "Mendaftar Konsultasi untuk " + saved.getTitle());
        } catch (Exception e) {
            System.err.println("Failed to log counseling booking interaction: " + e.getMessage());
        }

        return mapper.toDto(domain);
    }
}
