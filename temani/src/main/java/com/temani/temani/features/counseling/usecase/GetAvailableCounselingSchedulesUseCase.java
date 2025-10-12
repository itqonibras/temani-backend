package com.temani.temani.features.counseling.usecase;

import java.util.List;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetAvailableCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute();
}
