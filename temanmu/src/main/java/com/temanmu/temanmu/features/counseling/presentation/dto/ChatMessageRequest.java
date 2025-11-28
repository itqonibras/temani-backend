package com.temanmu.temanmu.features.counseling.presentation.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ChatMessageRequest {
    private String sessionId;
    private UUID receiverId; // Changed from receiverUsername to receiverId
    private String content;
}