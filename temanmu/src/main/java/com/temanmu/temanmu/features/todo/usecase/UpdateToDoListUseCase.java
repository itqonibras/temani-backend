package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoListRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

public interface UpdateToDoListUseCase {
    ToDoListResponse execute(UUID listId, ToDoListRequest request, UUID userId);
}