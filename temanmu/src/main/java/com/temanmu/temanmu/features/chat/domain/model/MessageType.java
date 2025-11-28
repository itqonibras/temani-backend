package com.temanmu.temanmu.features.chat.domain.model;

public enum MessageType {
    TEXT, // Plain text message
    IMAGE, // Image message
    FILE, // File attachment
    SYSTEM, // System message (e.g., user joined)
    NOTIFICATION, // Notification message
    EMERGENCY // Emergency/urgent message
}
