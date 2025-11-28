package com.temanmu.temanmu.features.chat.domain.model;

public enum ParticipantStatus {
    ACTIVE, // Currently active in the chat
    OFFLINE, // Not currently online
    AWAY, // Away but may return
    DO_NOT_DISTURB, // Do not disturb mode
    LEFT // Has left the chat room
}
