package com.temani.temani.features.counseling.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CounselingScheduleRequest {

    // Optional for PEER users (they are the counselor), required for CLIENT users
    private UUID counselorId;

    @NotNull
    @Future
    private LocalDateTime scheduledAt;

    @NotBlank
    private String title;

    private String description;

    private String meetingLink;

    private String notes;
}
