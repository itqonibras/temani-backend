package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface CreateCounselingScheduleUseCase {

    CounselingScheduleResponse execute(UUID requesterId, CounselingScheduleRequest request);
}
