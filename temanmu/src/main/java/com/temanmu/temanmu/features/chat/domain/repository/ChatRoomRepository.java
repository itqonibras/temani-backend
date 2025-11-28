package com.temanmu.temanmu.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temanmu.temanmu.features.chat.domain.model.ChatRoom;
import com.temanmu.temanmu.features.chat.domain.model.ChatRoomStatus;
import com.temanmu.temanmu.features.chat.domain.model.ChatRoomType;

public interface ChatRoomRepository {
    ChatRoom save(ChatRoom chatRoom);

    Optional<ChatRoom> findById(UUID id);

    List<ChatRoom> findByParticipantId(UUID userId);

    List<ChatRoom> findByType(ChatRoomType type);

    List<ChatRoom> findByStatus(ChatRoomStatus status);

    List<ChatRoom> findByCreatedBy(UUID createdBy);

    Optional<ChatRoom> findDirectMessageRoom(UUID user1Id, UUID user2Id);

    void delete(ChatRoom chatRoom);

    boolean existsById(UUID id);
}
