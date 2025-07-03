package com.temani.temani.features.counseling.presentation.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ChatMessageRequest {
    private String sessionId;
    private String receiverUsername;
    private String content;
}