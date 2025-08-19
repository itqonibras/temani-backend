package com.temani.temani.features.interactionlog.domain.repository;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.interactionlog.domain.model.InteractionLog;

public interface InteractionLogRepository {

    InteractionLog save(InteractionLog interactionLog);

    List<InteractionLog> findAllByUserId(UUID userId);

    List<InteractionLog> findAllByUserIdAndFeature(UUID userId, String feature);

}