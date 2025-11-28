package com.temanmu.temanmu.features.counseling.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetCaregiverCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute(UUID caregiverId);
}
