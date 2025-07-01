package com.temani.temani.features.todo.usecase;

import java.util.UUID;

import com.temani.temani.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;

public interface CreateToDoItemUseCase {
    ToDoItemResponse execute(UUID listId, ToDoItemRequest request, UUID userId);
} 