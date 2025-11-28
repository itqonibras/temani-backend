package com.temanmu.temanmu.features.chat.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.chat.domain.model.ChatRoom;
import com.temanmu.temanmu.features.chat.domain.model.ChatRoomType;

public interface CreateChatRoomUseCase {
    ChatRoom execute(CreateChatRoomRequest request);

    record CreateChatRoomRequest(
            String name,
            String description,
            ChatRoomType type,
            UUID createdBy,
            List<UUID> participantIds,
            ChatRoomSettingsRequest settings) {
    }

    record ChatRoomSettingsRequest(
            boolean allowInvites,
            boolean allowFileUploads,
            boolean allowMessageEditing,
            boolean allowMessageDeletion,
            int maxParticipants,
            int messageRetentionDays,
            boolean requireApproval) {
    }
}
