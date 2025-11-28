package com.temanmu.temanmu.features.counseling.usecase;

import java.util.UUID;

public interface DeleteCounselingScheduleUseCase {

    void execute(UUID scheduleId, UUID requesterId);
}
