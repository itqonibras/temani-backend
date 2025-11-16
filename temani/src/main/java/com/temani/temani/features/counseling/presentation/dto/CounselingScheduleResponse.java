package com.temani.temani.features.counseling.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounselingScheduleResponse {

    private UUID id;
    private UUID clientId;
    private String clientName;
    private UUID counselorId;
    private String counselorName;
    private LocalDateTime scheduledAt;
    private String title;
    private String description;
    private String meetingLink;
    private String notes;
    private String status;
}
