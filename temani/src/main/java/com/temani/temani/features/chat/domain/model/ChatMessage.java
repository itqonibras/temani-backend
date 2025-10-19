package com.temani.temani.features.chat.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ChatMessage {
    private UUID id;
    private UUID chatRoomId;
    private UUID senderId;
    private String senderUsername;
    private String content;
    private MessageType type;
    private MessageStatus status;
    private LocalDateTime timestamp;
    private LocalDateTime editedAt;
    private LocalDateTime deletedAt;
    private UUID replyToMessageId;
    private List<MessageAttachment> attachments;
    private List<MessageReaction> reactions;

    public ChatMessage(UUID id, UUID chatRoomId, UUID senderId, String senderUsername,
            String content, MessageType type, MessageStatus status,
            LocalDateTime timestamp, LocalDateTime editedAt,
            LocalDateTime deletedAt, UUID replyToMessageId,
            List<MessageAttachment> attachments, List<MessageReaction> reactions) {
        this.id = id;
        this.chatRoomId = chatRoomId;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.content = content;
        this.type = type;
        this.status = status;
        this.timestamp = timestamp;
        this.editedAt = editedAt;
        this.deletedAt = deletedAt;
        this.replyToMessageId = replyToMessageId;
        this.attachments = attachments;
        this.reactions = reactions;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getChatRoomId() {
        return chatRoomId;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public UUID getReplyToMessageId() {
        return replyToMessageId;
    }

    public List<MessageAttachment> getAttachments() {
        return attachments;
    }

    public List<MessageReaction> getReactions() {
        return reactions;
    }
}
