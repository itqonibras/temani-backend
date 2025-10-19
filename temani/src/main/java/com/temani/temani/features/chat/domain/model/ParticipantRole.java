package com.temani.temani.features.chat.domain.model;

public enum ParticipantRole {
    ADMIN, // Can manage the room and all participants
    MODERATOR, // Can moderate messages and manage participants
    MEMBER, // Regular participant
    OBSERVER // Read-only access
}
