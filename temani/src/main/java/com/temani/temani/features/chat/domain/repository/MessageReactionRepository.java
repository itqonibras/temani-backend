package com.temani.temani.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.MessageReaction;

public interface MessageReactionRepository {
    MessageReaction save(MessageReaction reaction);

    Optional<MessageReaction> findById(UUID id);

    List<MessageReaction> findByMessageId(UUID messageId);

    List<MessageReaction> findByUserId(UUID userId);

    Optional<MessageReaction> findByMessageIdAndUserId(UUID messageId, UUID userId);

    void delete(MessageReaction reaction);

    boolean existsByMessageIdAndUserId(UUID messageId, UUID userId);
}
