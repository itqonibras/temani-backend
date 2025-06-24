package com.temani.temani.features.journal.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Journal {

	private UUID id;

	private UUID userId;

	private String title;

	private String content;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Journal(UUID id, UUID userId, String title, String content, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

}
