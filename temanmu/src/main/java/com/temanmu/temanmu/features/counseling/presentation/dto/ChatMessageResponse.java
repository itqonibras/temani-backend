package com.temanmu.temanmu.features.counseling.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageResponse {
    private UUID id;
    private String sessionId;
    private UUID senderId; // Changed from senderUsername to senderId
    private String senderUsername; // Keep for display purposes
    private UUID receiverId; // Changed from receiverUsername to receiverId
    private String receiverUsername; // Keep for display purposes
    private String content;
    private LocalDateTime timestamp;
}