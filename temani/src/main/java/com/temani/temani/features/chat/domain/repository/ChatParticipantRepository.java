package com.temani.temani.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatParticipant;
import com.temani.temani.features.chat.domain.model.ParticipantStatus;

public interface ChatParticipantRepository {
    ChatParticipant save(ChatParticipant participant);

    Optional<ChatParticipant> findById(UUID id);

    List<ChatParticipant> findByChatRoomId(UUID chatRoomId);

    List<ChatParticipant> findByUserId(UUID userId);

    Optional<ChatParticipant> findByChatRoomIdAndUserId(UUID chatRoomId, UUID userId);

    List<ChatParticipant> findByChatRoomIdAndStatus(UUID chatRoomId, ParticipantStatus status);

    void delete(ChatParticipant participant);

    boolean existsByChatRoomIdAndUserId(UUID chatRoomId, UUID userId);
}
