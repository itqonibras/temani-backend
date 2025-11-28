package com.temanmu.temanmu.features.payment.usecase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;

import com.temanmu.temanmu.common.enums.PaymentStatus;
import com.temanmu.temanmu.features.counseling.infrastructure.persistence.CounselingScheduleJpaRepository;
import com.temanmu.temanmu.features.interactionlog.domain.service.InteractionLogService;
import com.temanmu.temanmu.features.payment.domain.model.Payment;
import com.temanmu.temanmu.features.payment.domain.repository.PaymentRepository;
import com.temanmu.temanmu.features.payment.infrastructure.mapper.PaymentDtoMapper;
import com.temanmu.temanmu.features.payment.presentation.dto.PaymentRequest;
import com.temanmu.temanmu.features.payment.presentation.dto.PaymentResponse;
import com.temanmu.temanmu.features.profile.infrastructure.persistence.UserJpaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentDtoMapper paymentDtoMapper;
    private final CounselingScheduleJpaRepository scheduleRepository;
    private final UserJpaRepository userRepository;
    private final WebClient webClient;
    private final InteractionLogService interactionLogService;

    @Value("${temanmu.payment.consultationFee}")
    private BigDecimal consultationFee;

    @Value("${temanmu.payment.maxFee:1000000.00}")
    private BigDecimal maxFee;

    @Value("${temanmu.payment.midtrans.serverKey}")
    private String midtransServerKey;

    @Override
    public PaymentResponse createPayment(PaymentRequest request) {
        try {
            // Validate fee amount
            if (request.getFee().compareTo(BigDecimal.valueOf(10000)) < 0) {
                throw new RuntimeException("Fee must be at least Rp 10,000");
            }
            if (request.getFee().compareTo(maxFee) > 0) {
                throw new RuntimeException("Fee cannot exceed Rp " + maxFee);
            }

            // Validate schedule exists and is available
            var schedule = scheduleRepository.findById(request.getScheduleId())
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));

            if (schedule.getStatus() != com.temanmu.temanmu.common.enums.CounselingScheduleStatus.AVAILABLE) {
                throw new RuntimeException("Schedule is not available for booking");
            }

            // Assign client to the schedule (if not already assigned)
            if (schedule.getClient() == null) {
                var client = userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new RuntimeException("Client not found"));
                schedule.setClient(client);
                // Keep status as AVAILABLE until payment is successful
                schedule.setStatus(com.temanmu.temanmu.common.enums.CounselingScheduleStatus.AVAILABLE);
                scheduleRepository.save(schedule);
            }

            // Generate unique order ID (Midtrans limit: 50 characters)
            String orderId = "Temanmu-" + request.getScheduleId().toString().substring(0, 8) + "-" +
                    String.valueOf(System.currentTimeMillis()).substring(8);

            // Create transaction request for Midtrans Snap API
            var transactionRequest = java.util.Map.of(
                    "transaction_details", java.util.Map.of(
                            "order_id", orderId,
                            "gross_amount", request.getFee()),
                    "customer_details", java.util.Map.of(
                            "email", "client@temanmu.com",
                            "first_name", "Client",
                            "last_name", "TemanMu"),
                    "item_details", java.util.Arrays.asList(
                            java.util.Map.of(
                                    "id", "CONSULTATION-001",
                                    "price", request.getFee(),
                                    "quantity", 1,
                                    "name", "Mental Health Consultation Session")),
                    "enabled_payments", java.util.Arrays.asList(
                            "credit_card", "bca_va", "bni_va", "bri_va", "echannel", "permata_va",
                            "gopay", "shopeepay", "qris"));

            // Call Midtrans Snap API
            var response = webClient.post()
                    .uri("https://app.sandbox.midtrans.com/snap/v1/transactions")
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .header("Authorization",
                            "Basic " + java.util.Base64.getEncoder()
                                    .encodeToString((midtransServerKey + ":").getBytes()))
                    .bodyValue(transactionRequest)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(errorBody -> {
                                        log.error("Midtrans API Error: {} - {}", clientResponse.statusCode(),
                                                errorBody);
                                        throw new RuntimeException("Midtrans API Error: " + clientResponse.statusCode()
                                                + " - " + errorBody);
                                    }))
                    .bodyToMono(java.util.Map.class)
                    .block();

            String token = response.get("token").toString();
            String redirectUrl = response.get("redirect_url").toString();

            // Create payment record
            var payment = new Payment(
                    null, // ID will be auto-generated by JPA
                    request.getScheduleId(),
                    request.getUserId(),
                    orderId,
                    null, // transaction ID will be set after payment
                    request.getFee(),
                    PaymentStatus.PENDING,
                    null, // payment method will be set after payment
                    null, // payment type will be set after payment
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    null, // paid at will be set after payment
                    null, // failure reason
                    redirectUrl,
                    token);

            var savedPayment = paymentRepository.save(payment);

            // Keep schedule status as AVAILABLE until payment is successful
            // Schedule status will be updated to SCHEDULED only after successful payment
            // verification

            return paymentDtoMapper.toDto(savedPayment);

        } catch (Exception e) {
            log.error("Payment creation error: {}", e.getMessage(), e);
            throw new RuntimeException("Payment creation failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse verifyPayment(String orderId, String transactionStatus) {
        try {
            var payment = paymentRepository.findByMidtransOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            // Update payment status based on Midtrans response
            PaymentStatus newStatus = mapMidtransStatusToPaymentStatus(transactionStatus);
            payment.setStatus(newStatus);
            payment.setUpdatedAt(LocalDateTime.now());

            if (newStatus == PaymentStatus.SETTLED || newStatus == PaymentStatus.CAPTURE) {
                payment.setPaidAt(LocalDateTime.now());

                // Update schedule status to SCHEDULED
                var schedule = scheduleRepository.findById(payment.getScheduleId())
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                schedule.setStatus(com.temanmu.temanmu.common.enums.CounselingScheduleStatus.SCHEDULED);
                scheduleRepository.save(schedule);

                // Log the payment interaction
                try {
                    interactionLogService.logInteraction(
                            payment.getUserId(),
                            "counseling",
                            "payment",
                            "counselingschedule",
                            payment.getScheduleId(),
                            "Membayar Konsultasi",
                            "Membayar Konsultasi untuk " + schedule.getTitle());
                } catch (Exception e) {
                    System.err.println("Failed to log payment interaction: " + e.getMessage());
                }

            } else if (newStatus == PaymentStatus.DENY || newStatus == PaymentStatus.FAILURE) {
                payment.setFailureReason("Payment failed: " + transactionStatus);

                // Update schedule status back to AVAILABLE
                var schedule = scheduleRepository.findById(payment.getScheduleId())
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                schedule.setStatus(com.temanmu.temanmu.common.enums.CounselingScheduleStatus.AVAILABLE);
                schedule.setClient(null); // Remove client assignment
                scheduleRepository.save(schedule);
            }

            var savedPayment = paymentRepository.save(payment);
            return paymentDtoMapper.toDto(savedPayment);

        } catch (Exception e) {
            log.error("Payment verification error: {}", e.getMessage(), e);
            throw new RuntimeException("Payment verification failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse verifyPayment(String orderId, String transactionStatus, String transactionId,
            String paymentType) {
        try {
            var payment = paymentRepository.findByMidtransOrderId(orderId)
                    .orElseThrow(() -> new RuntimeException("Payment not found"));

            // Update payment status based on Midtrans response
            PaymentStatus newStatus = mapMidtransStatusToPaymentStatus(transactionStatus);
            payment.setStatus(newStatus);
            payment.setUpdatedAt(LocalDateTime.now());

            // Set additional payment information
            if (transactionId != null) {
                payment.setMidtransTransactionId(transactionId);
            }
            if (paymentType != null) {
                payment.setPaymentType(paymentType);
            }

            if (newStatus == PaymentStatus.SETTLED || newStatus == PaymentStatus.CAPTURE) {
                payment.setPaidAt(LocalDateTime.now());

                // Update schedule status to SCHEDULED
                var schedule = scheduleRepository.findById(payment.getScheduleId())
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                schedule.setStatus(com.temanmu.temanmu.common.enums.CounselingScheduleStatus.SCHEDULED);
                scheduleRepository.save(schedule);

                // Log the payment interaction
                try {
                    interactionLogService.logInteraction(
                            payment.getUserId(),
                            "counseling",
                            "payment",
                            "counselingschedule",
                            payment.getScheduleId(),
                            "Membayar Konsultasi",
                            "Membayar Konsultasi untuk " + schedule.getTitle());
                } catch (Exception e) {
                    System.err.println("Failed to log payment interaction: " + e.getMessage());
                }

            } else if (newStatus == PaymentStatus.DENY || newStatus == PaymentStatus.FAILURE) {
                payment.setFailureReason("Payment failed: " + transactionStatus);

                // Update schedule status back to AVAILABLE
                var schedule = scheduleRepository.findById(payment.getScheduleId())
                        .orElseThrow(() -> new RuntimeException("Schedule not found"));
                schedule.setStatus(com.temanmu.temanmu.common.enums.CounselingScheduleStatus.AVAILABLE);
                schedule.setClient(null); // Remove client assignment
                scheduleRepository.save(schedule);
            }

            var savedPayment = paymentRepository.save(payment);
            return paymentDtoMapper.toDto(savedPayment);

        } catch (Exception e) {
            log.error("Payment verification error: {}", e.getMessage(), e);
            throw new RuntimeException("Payment verification failed: " + e.getMessage());
        }
    }

    @Override
    public PaymentResponse getPaymentStatus(String orderId) {
        var payment = paymentRepository.findByMidtransOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        return paymentDtoMapper.toDto(payment);
    }

    @Override
    public BigDecimal getConsultationFee() {
        return consultationFee;
    }

    private PaymentStatus mapMidtransStatusToPaymentStatus(String midtransStatus) {
        return switch (midtransStatus.toLowerCase()) {
            case "settlement" -> PaymentStatus.SETTLED;
            case "capture" -> PaymentStatus.CAPTURE;
            case "pending" -> PaymentStatus.PENDING;
            case "deny" -> PaymentStatus.DENY;
            case "cancel" -> PaymentStatus.CANCEL;
            case "expire" -> PaymentStatus.EXPIRE;
            case "failure" -> PaymentStatus.FAILURE;
            default -> PaymentStatus.PENDING;
        };
    }
}
