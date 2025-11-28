package com.temanmu.temanmu.features.payment.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.common.enums.PaymentStatus;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {

    Optional<PaymentEntity> findByMidtransOrderId(String midtransOrderId);

    Optional<PaymentEntity> findByScheduleId(UUID scheduleId);

    Optional<PaymentEntity> findByScheduleIdAndUserId(UUID scheduleId, UUID userId);

    List<PaymentEntity> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<PaymentEntity> findByStatus(PaymentStatus status);

    Optional<PaymentEntity> findByScheduleIdAndStatus(UUID scheduleId, PaymentStatus status);
}
