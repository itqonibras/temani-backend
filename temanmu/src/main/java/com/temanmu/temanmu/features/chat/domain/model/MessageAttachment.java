package com.temanmu.temanmu.features.chat.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MessageAttachment {
    private UUID id;
    private UUID messageId;
    private String fileName;
    private String fileUrl;
    private String fileType;
    private long fileSize;
    private AttachmentType type;
    private LocalDateTime uploadedAt;

    public MessageAttachment(UUID id, UUID messageId, String fileName,
            String fileUrl, String fileType, long fileSize,
            AttachmentType type, LocalDateTime uploadedAt) {
        this.id = id;
        this.messageId = messageId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.type = type;
        this.uploadedAt = uploadedAt;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileType() {
        return fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public AttachmentType getType() {
        return type;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
}
