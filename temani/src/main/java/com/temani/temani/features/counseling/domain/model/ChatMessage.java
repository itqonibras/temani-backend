package com.temani.temani.features.counseling.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ChatMessage {
    private UUID id;
    private String sessionId;
    private String senderUsername;
    private String receiverUsername;
    private String content;
    private LocalDateTime timestamp;

    public ChatMessage(UUID id, String sessionId, String senderUsername, String receiverUsername, String content, LocalDateTime timestamp) {
        this.id = id;
        this.sessionId = sessionId;
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.content = content;
        this.timestamp = timestamp;
    }

    public UUID getId() { return id; }
    public String getSessionId() { return sessionId; }
    public String getSenderUsername() { return senderUsername; }
    public String getReceiverUsername() { return receiverUsername; }
    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
} 