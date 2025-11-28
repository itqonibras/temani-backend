package com.temanmu.temanmu.features.payment.presentation.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {

    @NotNull
    private UUID scheduleId;

    @NotNull
    @DecimalMin(value = "10000.00", message = "Fee must be at least Rp 10,000")
    @DecimalMax(value = "1000000.00", message = "Fee cannot exceed Rp 1,000,000")
    private BigDecimal fee;

    // userId is set by the controller from authenticated user, so no validation
    // needed
    private UUID userId;
}
