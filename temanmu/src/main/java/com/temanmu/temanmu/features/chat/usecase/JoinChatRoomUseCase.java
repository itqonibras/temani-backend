package com.temanmu.temanmu.features.chat.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.chat.domain.model.ChatParticipant;
import com.temanmu.temanmu.features.chat.domain.model.ParticipantRole;

public interface JoinChatRoomUseCase {
    ChatParticipant execute(JoinChatRoomRequest request);

    record JoinChatRoomRequest(
            UUID chatRoomId,
            UUID userId,
            String username,
            ParticipantRole role) {
    }
}
