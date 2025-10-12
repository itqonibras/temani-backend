package com.temani.temani.features.counseling.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetClientCounselingSchedulesUseCase {

    List<CounselingScheduleResponse> execute(UUID clientId);
}
