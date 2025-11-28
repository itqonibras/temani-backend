package com.temanmu.temanmu.features.payment.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temanmu.temanmu.features.payment.domain.model.Payment;
import com.temanmu.temanmu.features.payment.domain.repository.PaymentRepository;
import com.temanmu.temanmu.features.payment.infrastructure.mapper.PaymentEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository jpaRepository;
    private final PaymentEntityMapper mapper;

    @Override
    public Payment save(Payment payment) {
        var entity = mapper.toEntity(payment);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByMidtransOrderId(String midtransOrderId) {
        return jpaRepository.findByMidtransOrderId(midtransOrderId).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByScheduleId(UUID scheduleId) {
        return jpaRepository.findByScheduleId(scheduleId).map(mapper::toDomain);
    }

    @Override
    public Optional<Payment> findByScheduleIdAndUserId(UUID scheduleId, UUID userId) {
        return jpaRepository.findByScheduleIdAndUserId(scheduleId, userId).map(mapper::toDomain);
    }

    @Override
    public List<Payment> findByUserId(UUID userId) {
        return jpaRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Payment> findByScheduleIdAndStatus(UUID scheduleId,
            com.temanmu.temanmu.common.enums.PaymentStatus status) {
        return jpaRepository.findByScheduleIdAndStatus(scheduleId, status).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void delete(Payment payment) {
        var entity = mapper.toEntity(payment);
        jpaRepository.delete(entity);
    }
}
