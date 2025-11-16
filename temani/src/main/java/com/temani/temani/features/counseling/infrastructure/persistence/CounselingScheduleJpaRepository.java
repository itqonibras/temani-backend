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

    @Query("SELECT cs FROM CounselingScheduleEntity cs LEFT JOIN FETCH cs.client LEFT JOIN FETCH cs.counselor WHERE cs.client.id = :clientId ORDER BY cs.scheduledAt DESC")
    List<CounselingScheduleEntity> findAllByClientIdOrderByScheduledAtDesc(@Param("clientId") UUID clientId);

    @Query("SELECT cs FROM CounselingScheduleEntity cs LEFT JOIN FETCH cs.client LEFT JOIN FETCH cs.counselor WHERE cs.counselor.id = :counselorId ORDER BY cs.scheduledAt DESC")
    List<CounselingScheduleEntity> findAllByCounselorIdOrderByScheduledAtDesc(@Param("counselorId") UUID counselorId);

    // Check for existing schedule at the same time for the same counselor
    @Query("SELECT cs FROM CounselingScheduleEntity cs WHERE cs.counselor.id = :counselorId " +
            "AND cs.scheduledAt = :scheduledAt")
    Optional<CounselingScheduleEntity> findByCounselorIdAndScheduledAt(
            @Param("counselorId") UUID counselorId,
            @Param("scheduledAt") LocalDateTime scheduledAt);

    // Find available schedules (status = AVAILABLE)
    @Query("SELECT cs FROM CounselingScheduleEntity cs LEFT JOIN FETCH cs.client LEFT JOIN FETCH cs.counselor WHERE cs.status = :status ORDER BY cs.scheduledAt ASC")
    List<CounselingScheduleEntity> findByStatusOrderByScheduledAtAsc(@Param("status") CounselingScheduleStatus status);

    // Find available schedules for a specific counselor
    @Query("SELECT cs FROM CounselingScheduleEntity cs LEFT JOIN FETCH cs.client LEFT JOIN FETCH cs.counselor WHERE cs.counselor.id = :counselorId AND cs.status = :status ORDER BY cs.scheduledAt ASC")
    List<CounselingScheduleEntity> findByCounselorIdAndStatusOrderByScheduledAtAsc(
            @Param("counselorId") UUID counselorId, @Param("status") CounselingScheduleStatus status);

    // Find by ID with eager fetching of client and counselor
    @Query("SELECT cs FROM CounselingScheduleEntity cs LEFT JOIN FETCH cs.client LEFT JOIN FETCH cs.counselor WHERE cs.id = :id")
    Optional<CounselingScheduleEntity> findByIdWithRelations(@Param("id") UUID id);
}
