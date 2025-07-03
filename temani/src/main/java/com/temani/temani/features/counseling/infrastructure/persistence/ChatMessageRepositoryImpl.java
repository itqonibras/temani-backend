package com.temani.temani.features.counseling.infrastructure.persistence;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.counseling.domain.model.ChatMessage;
import com.temani.temani.features.counseling.domain.repository.ChatMessageRepository;
import com.temani.temani.features.counseling.infrastructure.mapper.ChatMessageEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
    private final ChatMessageJpaRepository jpaRepository;
    private final ChatMessageEntityMapper mapper;

    @Override
    public ChatMessage save(ChatMessage message) {
        var entity = mapper.toEntity(message);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<ChatMessage> findAllBySessionId(String sessionId) {
        return jpaRepository.findAllBySessionId(sessionId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ChatMessage> findAllBySenderUsername(String senderUsername) {
        return jpaRepository.findAllBySenderUsername(senderUsername).stream().map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessage> findAllByReceiverUsername(String receiverUsername) {
        return jpaRepository.findAllByReceiverUsername(receiverUsername).stream().map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(ChatMessage message) {
        var entity = mapper.toEntity(message);
        jpaRepository.delete(entity);
    }

    @Override
    public ChatMessage findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain).orElse(null);
    }
}