package com.temani.temani.features.todo.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoListResponse {

	private UUID id;

	private UUID userId;

	private String title;

	private Boolean isShared;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private List<ToDoItemResponse> items;

} 