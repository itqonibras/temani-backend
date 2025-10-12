package com.temani.temani.features.counseling.usecase;

import java.util.UUID;

public interface DeleteCounselingScheduleUseCase {

    void execute(UUID scheduleId, UUID requesterId);
}
