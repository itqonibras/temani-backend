package com.temani.temani.features.todo.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class ToDoItem {

	private UUID id;

	private UUID toDoListId;

	private String description;

	private Boolean isComplete;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public ToDoItem(UUID id, UUID toDoListId, String description, Boolean isComplete, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.id = id;
		this.toDoListId = toDoListId;
		this.description = description;
		this.isComplete = isComplete;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public UUID getId() {
		return id;
	}

	public UUID getToDoListId() {
		return toDoListId;
	}

	public String getDescription() {
		return description;
	}

	public Boolean getIsComplete() {
		return isComplete;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

} 