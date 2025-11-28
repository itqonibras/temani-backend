package com.temanmu.temanmu.features.chat.domain.model;

public enum MessageStatus {
    SENT, // Message sent successfully
    DELIVERED, // Message delivered to recipient
    READ, // Message read by recipient
    FAILED, // Message failed to send
    PENDING, // Message pending delivery
    DELETED // Message deleted (soft delete)
}
