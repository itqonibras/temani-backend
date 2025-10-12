package com.temani.temani.features.payment.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.payment.domain.model.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(UUID id);

    Optional<Payment> findByMidtransOrderId(String midtransOrderId);

    Optional<Payment> findByScheduleId(UUID scheduleId);

    Optional<Payment> findByScheduleIdAndUserId(UUID scheduleId, UUID userId);

    List<Payment> findByUserId(UUID userId);

    List<Payment> findByScheduleIdAndStatus(UUID scheduleId, com.temani.temani.common.enums.PaymentStatus status);

    void delete(Payment payment);
}
