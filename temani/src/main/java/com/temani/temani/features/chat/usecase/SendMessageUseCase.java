package com.temani.temani.features.chat.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatMessage;
import com.temani.temani.features.chat.domain.model.MessageType;

public interface SendMessageUseCase {
    ChatMessage execute(SendMessageRequest request);

    record SendMessageRequest(
            UUID chatRoomId,
            UUID senderId,
            String content,
            MessageType type,
            UUID replyToMessageId,
            List<AttachmentRequest> attachments) {
    }

    record AttachmentRequest(
            String fileName,
            String fileUrl,
            String fileType,
            long fileSize) {
    }
}
