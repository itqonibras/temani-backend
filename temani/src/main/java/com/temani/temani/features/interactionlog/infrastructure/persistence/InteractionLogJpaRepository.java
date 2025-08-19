package com.temani.temani.features.interactionlog.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionLogJpaRepository extends JpaRepository<InteractionLogEntity, UUID> {

    List<InteractionLogEntity> findAllByUser_IdOrderByTimestampDesc(UUID userId);

    List<InteractionLogEntity> findAllByUser_IdAndFeatureOrderByTimestampDesc(UUID userId, String feature);

}