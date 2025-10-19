package com.temani.temani.features.chat.usecase;

import java.util.UUID;

import com.temani.temani.features.chat.domain.model.ChatParticipant;
import com.temani.temani.features.chat.domain.model.ParticipantRole;

public interface JoinChatRoomUseCase {
    ChatParticipant execute(JoinChatRoomRequest request);

    record JoinChatRoomRequest(
            UUID chatRoomId,
            UUID userId,
            String username,
            ParticipantRole role) {
    }
}
