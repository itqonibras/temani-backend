package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteCounselingScheduleUseCaseImpl implements DeleteCounselingScheduleUseCase {

    private final CounselingScheduleJpaRepository jpaRepository;

    @Override
    public void execute(UUID scheduleId, UUID requesterId) {
        var existing = jpaRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        if (!existing.getClient().getId().equals(requesterId) && !existing.getCounselor().getId().equals(requesterId)) {
            throw new RuntimeException("Access denied");
        }
        jpaRepository.delete(existing);
    }
}
