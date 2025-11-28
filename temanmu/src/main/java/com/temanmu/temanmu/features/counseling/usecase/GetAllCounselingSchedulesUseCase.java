package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import com.temanmu.temanmu.common.enums.CounselingScheduleStatus;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

import java.util.List;

public interface GetAllCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute(UUID userId, boolean isCaregiver, List<CounselingScheduleStatus> status);
}
