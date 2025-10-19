package com.temani.temani.features.chat.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChatRoom {
    private UUID id;
    private String name;
    private String description;
    private ChatRoomType type;
    private ChatRoomStatus status;
    private UUID createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ChatParticipant> participants;
    private ChatRoomSettings settings;

    public ChatRoom(UUID id, String name, String description, ChatRoomType type,
            ChatRoomStatus status, UUID createdBy, LocalDateTime createdAt,
            LocalDateTime updatedAt, List<ChatParticipant> participants,
            ChatRoomSettings settings) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.participants = participants;
        this.settings = settings;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ChatRoomType getType() {
        return type;
    }

    public ChatRoomStatus getStatus() {
        return status;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<ChatParticipant> getParticipants() {
        return participants;
    }

    public ChatRoomSettings getSettings() {
        return settings;
    }
}
