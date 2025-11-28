package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface GetCounselingScheduleByIdUseCase {

    CounselingScheduleResponse execute(UUID id, UUID requesterId);
}
