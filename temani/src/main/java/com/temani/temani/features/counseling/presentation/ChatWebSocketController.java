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
import com.temani.temani.features.profile.infrastructure.persistence.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
        private final SimpMessagingTemplate messagingTemplate;
        private final ChatMessageRepository chatMessageRepository;
        private final UserJpaRepository userJpaRepository;

        @MessageMapping("/chat.send")
        public void sendMessage(@Payload ChatMessageRequest request, Principal principal) {
                if (principal == null) {
                        System.err.println("WebSocket Principal is null! Message not sent.");
                        return;
                }

                System.out.println("=== CHAT MESSAGE DEBUG ===");
                System.out.println("Sender (from JWT): " + principal.getName());
                System.out.println("Receiver ID (from request): " + request.getReceiverId());
                System.out.println("Session ID: " + request.getSessionId());
                System.out.println("Content: " + request.getContent());

                // Get sender and receiver user details
                var senderUser = userJpaRepository.findByUsername(principal.getName())
                                .orElseThrow(() -> new RuntimeException("Sender user not found"));
                var receiverUser = userJpaRepository.findById(request.getReceiverId())
                                .orElseThrow(() -> new RuntimeException("Receiver user not found"));

                // Build and persist the message
                ChatMessage message = new ChatMessage(
                                UUID.randomUUID(),
                                request.getSessionId(),
                                senderUser.getId(),
                                senderUser.getName(),
                                receiverUser.getId(),
                                receiverUser.getName(),
                                request.getContent(),
                                LocalDateTime.now());
                chatMessageRepository.save(message);

                // Build response
                ChatMessageResponse response = new ChatMessageResponse(
                                message.getId(),
                                message.getSessionId(),
                                message.getSenderId(),
                                message.getSenderUsername(),
                                message.getReceiverId(),
                                message.getReceiverUsername(),
                                message.getContent(),
                                message.getTimestamp());

                System.out.println("Sending to receiver ID: " + request.getReceiverId());
                System.out.println("Sending to sender: " + principal.getName());

                // Send to receiver using session-specific destination
                messagingTemplate.convertAndSendToUser(
                                receiverUser.getUsername(),
                                "/queue/messages/" + request.getSessionId(),
                                response);
                // Optionally, send to sender as well (for echo)
                messagingTemplate.convertAndSendToUser(
                                principal.getName(),
                                "/queue/messages/" + request.getSessionId(),
                                response);

                System.out.println("Messages sent successfully!");
                System.out.println("=== END DEBUG ===");
        }
}