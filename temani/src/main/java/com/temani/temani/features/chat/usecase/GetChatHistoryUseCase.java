package com.temani.temani.features.chat.usecase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatMessage;

public interface GetChatHistoryUseCase {
    List<ChatMessage> execute(GetChatHistoryRequest request);

    record GetChatHistoryRequest(
            UUID chatRoomId,
            UUID userId,
            int page,
            int size,
            LocalDateTime beforeTimestamp) {
    }
}
