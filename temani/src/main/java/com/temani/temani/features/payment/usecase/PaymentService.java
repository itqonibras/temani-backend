package com.temani.temani.features.payment.usecase;

import java.math.BigDecimal;

import com.temani.temani.features.payment.presentation.dto.PaymentRequest;
import com.temani.temani.features.payment.presentation.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPayment(PaymentRequest request);

    PaymentResponse verifyPayment(String orderId, String transactionStatus);

    PaymentResponse verifyPayment(String orderId, String transactionStatus, String transactionId, String paymentType);

    PaymentResponse getPaymentStatus(String orderId);

    BigDecimal getConsultationFee();
}
