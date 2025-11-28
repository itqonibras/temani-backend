package com.temanmu.temanmu.features.counseling.presentation.dto;

import com.temanmu.temanmu.common.enums.CounselingScheduleStatus;

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

