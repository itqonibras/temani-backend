package com.temanmu.temanmu.features.counseling.usecase;

import java.util.List;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetAvailableCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute();
}
