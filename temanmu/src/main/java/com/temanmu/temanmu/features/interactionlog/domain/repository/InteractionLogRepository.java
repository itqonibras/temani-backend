package com.temanmu.temanmu.features.interactionlog.domain.repository;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.interactionlog.domain.model.InteractionLog;

public interface InteractionLogRepository {

    InteractionLog save(InteractionLog interactionLog);

    List<InteractionLog> findAllByUserId(UUID userId);

    List<InteractionLog> findAllByUserIdAndFeature(UUID userId, String feature);

}