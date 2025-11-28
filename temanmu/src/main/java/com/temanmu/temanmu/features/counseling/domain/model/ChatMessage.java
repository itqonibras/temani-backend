package com.temanmu.temanmu.features.counseling.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {
    private UUID id;
    private String sessionId;
    private UUID senderId;
    private String senderUsername;
    private UUID receiverId;
    private String receiverUsername;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessage(UUID id, String sessionId, UUID senderId, String senderUsername, UUID receiverId,
            String receiverUsername, String content, LocalDateTime timestamp) {
        this.id = id;
        this.sessionId = sessionId;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}