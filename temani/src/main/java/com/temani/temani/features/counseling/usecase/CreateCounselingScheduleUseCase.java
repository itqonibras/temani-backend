package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleRequest;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface CreateCounselingScheduleUseCase {

    CounselingScheduleResponse execute(UUID requesterId, CounselingScheduleRequest request);
}
