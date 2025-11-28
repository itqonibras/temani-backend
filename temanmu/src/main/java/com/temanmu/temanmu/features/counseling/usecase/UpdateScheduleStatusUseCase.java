package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

import com.temanmu.temanmu.common.enums.CounselingScheduleStatus;
import com.temanmu.temanmu.features.counseling.presentation.dto.CounselingScheduleResponse;

public interface UpdateScheduleStatusUseCase {

    CounselingScheduleResponse execute(UUID scheduleId, UUID peerId, CounselingScheduleStatus status);

}

