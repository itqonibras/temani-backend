package com.temani.temani.features.counseling.presentation.dto;

import com.temani.temani.common.enums.CounselingScheduleStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleStatusRequest {

    @NotNull(message = "Status is required")
    private CounselingScheduleStatus status;

}

