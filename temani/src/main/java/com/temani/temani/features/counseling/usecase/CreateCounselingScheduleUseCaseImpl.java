package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.counseling.domain.model.CounselingSchedule;
import com.temani.temani.features.counseling.infrastructure.mapper.CounselingScheduleDtoMapper;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleEntity;
import com.temani.temani.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;
import com.temani.temani.features.profile.infrastructure.persistence.UserEntity;
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateCounselingScheduleUseCaseImpl implements CreateCounselingScheduleUseCase {

    private final CounselingScheduleDtoMapper mapper;
    private final CounselingScheduleJpaRepository jpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public CounselingScheduleResponse execute(UUID requesterId, CounselingScheduleRequest request) {
        var requester = userJpaRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if requester is a PEER (creating available schedule) or CLIENT (booking
        // existing schedule)
        boolean requesterIsPeer = requester.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
        boolean requesterIsClient = requester.getRoles().stream()
                .anyMatch(r -> r.getName().equalsIgnoreCase("CLIENT"));

        if (!requesterIsPeer && !requesterIsClient) {
            throw new RuntimeException("Only PEER or CLIENT roles can create schedules");
        }

        UserEntity counselor;
        if (requesterIsPeer) {
            // PEER creating their own available schedule - they are the counselor
            counselor = requester;
        } else {
            // CLIENT booking a schedule - validate the counselor exists and has PEER role
            counselor = userJpaRepository.findById(request.getCounselorId())
                    .orElseThrow(() -> new RuntimeException("Counselor not found"));

            boolean counselorIsPeer = counselor.getRoles().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase("PEER"));
            if (!counselorIsPeer) {
                throw new RuntimeException("Counselor must have PEER role");
            }
        }

        // Check for duplicate time slot for the same counselor
        var existingSchedule = jpaRepository.findByCounselorIdAndScheduledAt(counselor.getId(),
                request.getScheduledAt());
        if (existingSchedule.isPresent()) {
            throw new RuntimeException("A schedule already exists for this counselor at the specified time");
        }

        CounselingScheduleEntity entity = new CounselingScheduleEntity();

        if (requesterIsPeer) {
            // PEER creating an available schedule
            entity.setClient(null); // No client assigned yet
            entity.setCounselor(counselor); // counselor is the requester (PEER)
            entity.setCounselorName(counselor.getName());
            entity.setStatus(com.temani.temani.common.enums.CounselingScheduleStatus.AVAILABLE);
        } else {
            // CLIENT booking a schedule (this would be handled by BookScheduleUseCase, but
            // keeping for backward compatibility)
            entity.setClient(requester);
            entity.setCounselor(counselor);
            entity.setCounselorName(counselor.getName());
            entity.setStatus(com.temani.temani.common.enums.CounselingScheduleStatus.SCHEDULED);
        }

        entity.setScheduledAt(request.getScheduledAt());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setMeetingLink(request.getMeetingLink());
        entity.setNotes(request.getNotes());

        var saved = jpaRepository.save(entity);
        var domain = new CounselingSchedule(saved.getId(),
                saved.getClient() != null ? saved.getClient().getId() : null,
                counselor.getId(),
                counselor.getName(),
                saved.getScheduledAt(),
                saved.getTitle(), saved.getDescription(), saved.getMeetingLink(), saved.getNotes(), saved.getStatus());
        return mapper.toDto(domain);
    }
}
