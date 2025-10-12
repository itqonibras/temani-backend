package com.temani.temani.features.payment.infrastructure.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.temani.temani.features.payment.domain.model.Payment;
import com.temani.temani.features.payment.presentation.dto.PaymentResponse;

@Mapper(componentModel = "spring")
public interface PaymentDtoMapper {

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
    PaymentResponse toDto(Payment domain);
}
