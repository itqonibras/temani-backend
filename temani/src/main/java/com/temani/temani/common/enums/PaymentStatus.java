package com.temani.temani.common.enums;

public enum PaymentStatus {
    PENDING, // Payment initiated but not completed
    SETTLED, // Payment completed successfully
    CAPTURE, // Payment captured
    DENY, // Payment denied
    CANCEL, // Payment cancelled
    EXPIRE, // Payment expired
    FAILURE // Payment failed
}
