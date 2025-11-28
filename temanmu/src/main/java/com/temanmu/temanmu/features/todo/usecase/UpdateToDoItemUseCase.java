package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

public interface UpdateToDoItemUseCase {
    ToDoItemResponse execute(UUID itemId, ToDoItemRequest request, UUID userId);
}