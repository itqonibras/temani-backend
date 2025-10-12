package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface BookCounselingScheduleUseCase {

    CounselingScheduleResponse execute(UUID scheduleId, UUID clientId);
}
