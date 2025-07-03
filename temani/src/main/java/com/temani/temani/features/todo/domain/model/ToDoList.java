package com.temani.temani.features.todo.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ToDoList {

	private UUID id;

	private UUID userId;

	private String title;

	private Boolean isShared;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private List<ToDoItem> items;

	public ToDoList(UUID id, UUID userId, String title, Boolean isShared, LocalDateTime createdAt,
			LocalDateTime updatedAt, List<ToDoItem> items) {
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.isShared = isShared;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.items = items;
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

	public Boolean getIsShared() {
		return isShared;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public List<ToDoItem> getItems() {
		return items;
	}

} 