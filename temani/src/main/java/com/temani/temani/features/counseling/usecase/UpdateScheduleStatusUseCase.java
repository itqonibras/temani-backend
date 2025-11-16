package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

import com.temani.temani.common.enums.CounselingScheduleStatus;
import com.temani.temani.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface UpdateScheduleStatusUseCase {

    CounselingScheduleResponse execute(UUID scheduleId, UUID peerId, CounselingScheduleStatus status);

}

