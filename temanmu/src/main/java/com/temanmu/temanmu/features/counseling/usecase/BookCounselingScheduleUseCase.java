package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface BookCounselingScheduleUseCase {

    CounselingScheduleResponse execute(UUID scheduleId, UUID clientId);
}
