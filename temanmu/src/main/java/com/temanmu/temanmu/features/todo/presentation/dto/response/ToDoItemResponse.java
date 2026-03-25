package com.temanmu.temanmu.features.todo.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemResponse {

	private UUID id;

	private UUID toDoListId;

	private String description;

	private String priority;

	private Boolean isComplete;

	private LocalDateTime scheduledAt;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}