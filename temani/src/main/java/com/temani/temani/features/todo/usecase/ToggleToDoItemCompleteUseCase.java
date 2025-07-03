package com.temani.temani.features.todo.usecase;

import java.util.UUID;

import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;

public interface ToggleToDoItemCompleteUseCase {
    ToDoItemResponse execute(UUID itemId, UUID userId);
} 