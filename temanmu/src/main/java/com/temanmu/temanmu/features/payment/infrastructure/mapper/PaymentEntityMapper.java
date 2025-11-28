package com.temanmu.temanmu.features.payment.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.payment.domain.model.Payment;
import com.temanmu.temanmu.features.payment.infrastructure.persistence.PaymentEntity;

@Mapper(componentModel = "spring")
public interface PaymentEntityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "midtransOrderId", source = "midtransOrderId")
    @Mapping(target = "midtransTransactionId", source = "midtransTransactionId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "paymentType", source = "paymentType")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "paidAt", source = "paidAt")
    @Mapping(target = "failureReason", source = "failureReason")
    @Mapping(target = "redirectUrl", source = "redirectUrl")
    @Mapping(target = "token", source = "token")
    Payment toDomain(PaymentEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "scheduleId", source = "scheduleId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "midtransOrderId", source = "midtransOrderId")
    @Mapping(target = "midtransTransactionId", source = "midtransTransactionId")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "paymentType", source = "paymentType")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "paidAt", source = "paidAt")
    @Mapping(target = "failureReason", source = "failureReason")
    @Mapping(target = "redirectUrl", source = "redirectUrl")
    @Mapping(target = "token", source = "token")
    PaymentEntity toEntity(Payment domain);
}
