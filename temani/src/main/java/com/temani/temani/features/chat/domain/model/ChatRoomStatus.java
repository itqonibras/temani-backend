package com.temani.temani.features.chat.domain.model;

public enum ChatRoomStatus {
    ACTIVE, // Room is active and accepting messages
    ARCHIVED, // Room is archived but accessible
    SUSPENDED, // Room is temporarily suspended
    DELETED // Room is deleted (soft delete)
}
