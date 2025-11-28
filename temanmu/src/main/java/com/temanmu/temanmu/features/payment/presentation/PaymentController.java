package com.temanmu.temanmu.features.payment.presentation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.constants.PaymentMessages;
import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.payment.domain.repository.PaymentRepository;
import com.temanmu.temanmu.features.payment.infrastructure.mapper.PaymentDtoMapper;
import com.temanmu.temanmu.features.payment.presentation.dto.PaymentRequest;
import com.temanmu.temanmu.features.payment.presentation.dto.PaymentResponse;
import com.temanmu.temanmu.features.payment.usecase.PaymentService;
import com.temanmu.temanmu.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentRepository paymentRepository;
    private final PaymentDtoMapper paymentDtoMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody @Valid PaymentRequest request, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        try {
            // Verify user is a CLIENT
            boolean isClient = user.getRoles().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase("CLIENT"));
            if (!isClient) {
                return ResponseEntity.badRequest()
                        .body(BaseResponse.error("Only clients can make payments"));
            }

            // Set the user ID from authenticated user
            request.setUserId(user.getId());

            PaymentResponse payment = paymentService.createPayment(request);
            return ResponseEntity.ok(BaseResponse.success(PaymentMessages.PAYMENT_CREATED_SUCCESS, payment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @RequestParam String order_id,
            @RequestParam String transaction_status) {
        try {
            PaymentResponse payment = paymentService.verifyPayment(order_id, transaction_status);
            return ResponseEntity.ok(BaseResponse.success(PaymentMessages.PAYMENT_VERIFIED_SUCCESS, payment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/status/{orderId}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String orderId) {
        try {
            PaymentResponse payment = paymentService.getPaymentStatus(orderId);
            return ResponseEntity.ok(BaseResponse.success("Payment status retrieved", payment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> getPaymentHistory(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        try {
            List<PaymentResponse> payments = paymentRepository.findByUserId(user.getId())
                    .stream()
                    .map(paymentDtoMapper::toDto)
                    .toList();

            return ResponseEntity.ok(BaseResponse.success(PaymentMessages.PAYMENT_HISTORY_RETRIEVED_SUCCESS, payments));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/fee")
    public ResponseEntity<?> getConsultationFee() {
        try {
            return ResponseEntity.ok(BaseResponse.success("Consultation fee",
                    java.util.Map.of("fee", paymentService.getConsultationFee())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}
