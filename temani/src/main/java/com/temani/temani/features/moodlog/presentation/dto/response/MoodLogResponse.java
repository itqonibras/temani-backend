package com.temani.temani.features.moodlog.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoodLogResponse {

	private UUID id;

	private UUID userId;

	private String moodVisual;

	private Integer emotionScale; // 1: sangat buruk, 2: buruk, 3: biasa saja, 4: baik, 5: sangat baik

	private LocalDateTime timestamp;

} 