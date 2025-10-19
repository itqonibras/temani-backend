package com.temani.temani.features.chat.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatRoom;

public interface GetChatRoomsUseCase {
    List<ChatRoom> execute(GetChatRoomsRequest request);

    record GetChatRoomsRequest(
            UUID userId,
            int page,
            int size,
            String searchQuery) {
    }
}
