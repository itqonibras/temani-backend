package com.temani.temani.features.counseling.usecase;

import java.util.UUID;
import java.util.List;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetAllCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute(UUID userId, boolean isCaregiver);
}
