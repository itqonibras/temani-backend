package com.temani.temani.features.counseling.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.temani.temani.common.enums.CounselingScheduleStatus;

@Repository
public interface CounselingScheduleJpaRepository extends JpaRepository<CounselingScheduleEntity, UUID> {

    List<CounselingScheduleEntity> findAllByClientIdOrderByScheduledAtDesc(UUID clientId);

    List<CounselingScheduleEntity> findAllByCounselorIdOrderByScheduledAtDesc(UUID counselorId);

    // Check for existing schedule at the same time for the same counselor
    @Query("SELECT cs FROM CounselingScheduleEntity cs WHERE cs.counselor.id = :counselorId " +
            "AND cs.scheduledAt = :scheduledAt")
    Optional<CounselingScheduleEntity> findByCounselorIdAndScheduledAt(
            @Param("counselorId") UUID counselorId,
            @Param("scheduledAt") LocalDateTime scheduledAt);

    // Find available schedules (status = AVAILABLE)
    List<CounselingScheduleEntity> findByStatusOrderByScheduledAtAsc(CounselingScheduleStatus status);

    // Find available schedules for a specific counselor
    List<CounselingScheduleEntity> findByCounselorIdAndStatusOrderByScheduledAtAsc(
            UUID counselorId, CounselingScheduleStatus status);
}
