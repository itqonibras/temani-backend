package com.temani.temani.features.payment.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.temani.temani.common.enums.PaymentStatus;

public class Payment {

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

    public Payment(UUID id, UUID scheduleId, UUID userId, String midtransOrderId,
            String midtransTransactionId, BigDecimal amount, PaymentStatus status,
            String paymentMethod, String paymentType, LocalDateTime createdAt,
            LocalDateTime updatedAt, LocalDateTime paidAt, String failureReason,
            String redirectUrl, String token) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.midtransOrderId = midtransOrderId;
        this.midtransTransactionId = midtransTransactionId;
        this.amount = amount;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.paymentType = paymentType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.paidAt = paidAt;
        this.failureReason = failureReason;
        this.redirectUrl = redirectUrl;
        this.token = token;
    }

    public UUID getId() {
        return id;
    }

    public UUID getScheduleId() {
        return scheduleId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMidtransOrderId() {
        return midtransOrderId;
    }

    public String getMidtransTransactionId() {
        return midtransTransactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public String getToken() {
        return token;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public void setMidtransTransactionId(String midtransTransactionId) {
        this.midtransTransactionId = midtransTransactionId;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
