package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface UpdateCounselingScheduleUseCase {

    CounselingScheduleResponse execute(UUID scheduleId, UUID requesterId, CounselingScheduleRequest request);
}
