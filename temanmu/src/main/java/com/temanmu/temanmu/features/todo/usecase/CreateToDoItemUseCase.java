package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

public interface CreateToDoItemUseCase {
    ToDoItemResponse execute(UUID listId, ToDoItemRequest request, UUID userId);
}