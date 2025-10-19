package com.temani.temani.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatMessage;
import com.temani.temani.features.chat.domain.model.MessageStatus;
import com.temani.temani.features.chat.domain.model.MessageType;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage message);

    Optional<ChatMessage> findById(UUID id);

    List<ChatMessage> findByChatRoomId(UUID chatRoomId);

    List<ChatMessage> findByChatRoomIdOrderByTimestampDesc(UUID chatRoomId);

    List<ChatMessage> findByChatRoomIdAndTimestampAfter(UUID chatRoomId, java.time.LocalDateTime timestamp);

    List<ChatMessage> findBySenderId(UUID senderId);

    List<ChatMessage> findBySenderIdAndChatRoomId(UUID senderId, UUID chatRoomId);

    List<ChatMessage> findByStatus(MessageStatus status);

    List<ChatMessage> findByType(MessageType type);

    List<ChatMessage> findByReplyToMessageId(UUID replyToMessageId);

    void delete(ChatMessage message);

    long countByChatRoomId(UUID chatRoomId);

    long countByChatRoomIdAndSenderId(UUID chatRoomId, UUID senderId);
}
