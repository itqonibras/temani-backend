package com.temani.temani.features.counseling.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessageEntity, UUID> {
    List<ChatMessageEntity> findAllBySessionId(String sessionId);
    List<ChatMessageEntity> findAllBySenderUsername(String senderUsername);
    List<ChatMessageEntity> findAllByReceiverUsername(String receiverUsername);
} 