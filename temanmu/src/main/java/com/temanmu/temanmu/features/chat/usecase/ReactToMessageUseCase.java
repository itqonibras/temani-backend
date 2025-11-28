package com.temanmu.temanmu.features.chat.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.chat.domain.model.MessageReaction;

public interface ReactToMessageUseCase {
    MessageReaction execute(ReactToMessageRequest request);

    record ReactToMessageRequest(
            UUID messageId,
            UUID userId,
            String username,
            String emoji) {
    }
}
