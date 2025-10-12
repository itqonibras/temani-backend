package com.temani.temani.features.counseling.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.temani.temani.common.enums.CounselingScheduleStatus;

public class CounselingSchedule {

    private UUID id;
    private UUID clientId;
    private UUID counselorId;
    private String counselorName;
    private LocalDateTime scheduledAt;
    private String title;
    private String description;
    private String meetingLink;
    private String notes;
    private CounselingScheduleStatus status;

    public CounselingSchedule(UUID id, UUID clientId, UUID counselorId, String counselorName, LocalDateTime scheduledAt,
            String title,
            String description, String meetingLink, String notes, CounselingScheduleStatus status) {
        this.id = id;
        this.clientId = clientId;
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.scheduledAt = scheduledAt;
        this.title = title;
        this.description = description;
        this.meetingLink = meetingLink;
        this.notes = notes;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getCounselorId() {
        return counselorId;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public String getNotes() {
        return notes;
    }

    public CounselingScheduleStatus getStatus() {
        return status;
    }
}
