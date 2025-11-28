package com.temanmu.temanmu.common.enums;

public enum CounselingScheduleStatus {
    AVAILABLE, // Peer created, waiting for client to book
    PENDING, // Client booked, waiting for payment
    SCHEDULED, // Payment confirmed, session scheduled
    ONGOING, // Session is happening
    COMPLETED, // Session finished
    CANCELLED // Either peer or client cancelled
}
