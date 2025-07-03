package com.temani.temani.features.counseling.presentation;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.features.counseling.domain.repository.ChatMessageRepository;
import com.temani.temani.features.counseling.presentation.dto.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-messages")
public class ChatHistoryController {
    private final ChatMessageRepository chatMessageRepository;

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<List<ChatMessageResponse>> getBySession(@PathVariable String sessionId) {
        var messages = chatMessageRepository.findAllBySessionId(sessionId)
                .stream()
                .map(m -> new ChatMessageResponse(
                        m.getId(),
                        m.getSessionId(),
                        m.getSenderUsername(),
                        m.getReceiverUsername(),
                        m.getContent(),
                        m.getTimestamp()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sent/{senderUsername}")
    public ResponseEntity<List<ChatMessageResponse>> getBySender(@PathVariable String senderUsername) {
        var messages = chatMessageRepository.findAllBySenderUsername(senderUsername)
                .stream()
                .map(m -> new ChatMessageResponse(
                        m.getId(),
                        m.getSessionId(),
                        m.getSenderUsername(),
                        m.getReceiverUsername(),
                        m.getContent(),
                        m.getTimestamp()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/received/{receiverUsername}")
    public ResponseEntity<List<ChatMessageResponse>> getByReceiver(@PathVariable String receiverUsername) {
        var messages = chatMessageRepository.findAllByReceiverUsername(receiverUsername)
                .stream()
                .map(m -> new ChatMessageResponse(
                        m.getId(),
                        m.getSessionId(),
                        m.getSenderUsername(),
                        m.getReceiverUsername(),
                        m.getContent(),
                        m.getTimestamp()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(messages);
    }
}