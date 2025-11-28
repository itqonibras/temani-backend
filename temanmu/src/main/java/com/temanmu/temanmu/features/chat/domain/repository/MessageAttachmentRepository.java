package com.temanmu.temanmu.features.chat.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temanmu.temanmu.features.chat.domain.model.AttachmentType;
import com.temanmu.temanmu.features.chat.domain.model.MessageAttachment;

public interface MessageAttachmentRepository {
    MessageAttachment save(MessageAttachment attachment);

    Optional<MessageAttachment> findById(UUID id);

    List<MessageAttachment> findByMessageId(UUID messageId);

    List<MessageAttachment> findByType(AttachmentType type);

    void delete(MessageAttachment attachment);
}
