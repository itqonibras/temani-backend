package com.temanmu.temanmu.features.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageReaction {
    private UUID id;
    private UUID messageId;
    private UUID userId;
    private String username;
    private String emoji;
    private LocalDateTime timestamp;

    public MessageReaction(UUID id, UUID messageId, UUID userId,
            String username, String emoji, LocalDateTime timestamp) {
        this.id = id;
        this.messageId = messageId;
        this.userId = userId;
        this.username = username;
        this.emoji = emoji;
        this.timestamp = timestamp;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmoji() {
        return emoji;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
