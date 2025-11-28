package com.temanmu.temanmu.features.counseling.domain.repository;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.counseling.domain.model.ChatMessage;

public interface ChatMessageRepository {
    ChatMessage save(ChatMessage message);
    List<ChatMessage> findAllBySessionId(String sessionId);
    List<ChatMessage> findAllBySenderUsername(String senderUsername);
    List<ChatMessage> findAllByReceiverUsername(String receiverUsername);
    void delete(ChatMessage message);
    ChatMessage findById(UUID id);
}