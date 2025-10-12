package com.temani.temani.features.payment.presentation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.temani.temani.common.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private UUID id;
    private UUID scheduleId;
    private UUID userId;
    private String midtransOrderId;
    private String midtransTransactionId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String paymentMethod;
    private String paymentType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime paidAt;
    private String failureReason;
    private String redirectUrl;
    private String token;
}
