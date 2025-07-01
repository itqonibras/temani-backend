package com.temani.temani.features.counseling.presentation.dto;

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
    private String senderUsername;
    private String receiverUsername;
    private String content;
    private LocalDateTime timestamp;
} 