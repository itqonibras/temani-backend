package com.temani.temani.features.counseling.presentation;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.temani.temani.features.counseling.domain.model.ChatMessage;
import com.temani.temani.features.counseling.domain.repository.ChatMessageRepository;
import com.temani.temani.features.counseling.presentation.dto.ChatMessageRequest;
import com.temani.temani.features.counseling.presentation.dto.ChatMessageResponse;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
        private final SimpMessagingTemplate messagingTemplate;
        private final ChatMessageRepository chatMessageRepository;

        @MessageMapping("/chat.send")
        public void sendMessage(@Payload ChatMessageRequest request, Principal principal) {
                if (principal == null) {
                        System.err.println("WebSocket Principal is null! Message not sent.");
                        return;
                }

                // Build and persist the message
                ChatMessage message = new ChatMessage(
                                UUID.randomUUID(),
                                request.getSessionId(),
                                principal.getName(),
                                request.getReceiverUsername(),
                                request.getContent(),
                                LocalDateTime.now());
                chatMessageRepository.save(message);

                // Build response
                ChatMessageResponse response = new ChatMessageResponse(
                                message.getId(),
                                message.getSessionId(),
                                message.getSenderUsername(),
                                message.getReceiverUsername(),
                                message.getContent(),
                                message.getTimestamp());

                // Send to receiver
                messagingTemplate.convertAndSendToUser(
                                request.getReceiverUsername(),
                                "/queue/messages",
                                response);
                // Optionally, send to sender as well (for echo)
                messagingTemplate.convertAndSendToUser(
                                principal.getName(),
                                "/queue/messages",
                                response);
        }
}