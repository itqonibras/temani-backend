package com.temani.temani.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.chat.domain.model.MessageAttachment;
import com.temani.temani.features.chat.domain.model.AttachmentType;

public interface MessageAttachmentRepository {
    MessageAttachment save(MessageAttachment attachment);

    Optional<MessageAttachment> findById(UUID id);

    List<MessageAttachment> findByMessageId(UUID messageId);

    List<MessageAttachment> findByType(AttachmentType type);

    void delete(MessageAttachment attachment);
}
