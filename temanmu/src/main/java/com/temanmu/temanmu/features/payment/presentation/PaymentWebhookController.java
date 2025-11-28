package com.temanmu.temanmu.features.payment.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.features.payment.usecase.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/payments/webhook")
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookController {

    private final PaymentService paymentService;

    @PostMapping("/midtrans")
    public ResponseEntity<?> midtransWebhook(@RequestBody java.util.Map<String, Object> webhookPayload) {
        try {
            log.info("Received Midtrans webhook: {}", webhookPayload);

            // Extract data from JSON payload
            String orderId = (String) webhookPayload.get("order_id");
            String transactionStatus = (String) webhookPayload.get("transaction_status");
            String transactionId = (String) webhookPayload.get("transaction_id");
            String paymentType = (String) webhookPayload.get("payment_type");

            if (orderId == null || transactionStatus == null) {
                log.error("Invalid webhook payload: missing order_id or transaction_status");
                return ResponseEntity.badRequest().body(BaseResponse.error("Invalid webhook payload"));
            }

            log.info("Processing payment notification - Order ID: {}, Status: {}, Transaction ID: {}, Payment Type: {}",
                    orderId, transactionStatus, transactionId, paymentType);

            // Verify and update payment status
            var paymentResponse = paymentService.verifyPayment(orderId, transactionStatus, transactionId, paymentType);

            log.info("Payment status updated successfully for order: {}", orderId);
            return ResponseEntity.ok(BaseResponse.success("Webhook processed successfully", paymentResponse));

        } catch (Exception e) {
            log.error("Webhook processing error: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(BaseResponse.error("Webhook processing failed"));
        }
    }

}
