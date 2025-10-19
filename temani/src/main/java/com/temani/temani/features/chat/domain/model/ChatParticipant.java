package com.temani.temani.features.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatParticipant {
    private UUID id;
    private UUID chatRoomId;
    private UUID userId;
    private String username;
    private ParticipantRole role;
    private ParticipantStatus status;
    private LocalDateTime joinedAt;
    private LocalDateTime lastSeenAt;
    private LocalDateTime leftAt;

    public ChatParticipant(UUID id, UUID chatRoomId, UUID userId, String username,
            ParticipantRole role, ParticipantStatus status,
            LocalDateTime joinedAt, LocalDateTime lastSeenAt,
            LocalDateTime leftAt) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.status = status;
        this.joinedAt = joinedAt;
        this.lastSeenAt = lastSeenAt;
        this.leftAt = leftAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getChatRoomId() {
        return chatRoomId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public ParticipantRole getRole() {
        return role;
    }

    public ParticipantStatus getStatus() {
        return status;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public LocalDateTime getLastSeenAt() {
        return lastSeenAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }
}
