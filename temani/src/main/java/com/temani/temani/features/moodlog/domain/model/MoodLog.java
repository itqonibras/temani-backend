package com.temani.temani.features.moodlog.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class MoodLog {

	private UUID id;

	private UUID userId;

	private String moodVisual;

	private Integer emotionScale; // 1: sangat buruk, 2: buruk, 3: biasa saja, 4: baik, 5: sangat baik

	private LocalDateTime timestamp;

	public MoodLog(UUID id, UUID userId, String moodVisual, Integer emotionScale, LocalDateTime timestamp) {
		this.id = id;
		this.userId = userId;
		this.moodVisual = moodVisual;
		this.emotionScale = emotionScale;
		this.timestamp = timestamp;
	}

	public UUID getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getMoodVisual() {
		return moodVisual;
	}

	public Integer getEmotionScale() {
		return emotionScale;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

} 