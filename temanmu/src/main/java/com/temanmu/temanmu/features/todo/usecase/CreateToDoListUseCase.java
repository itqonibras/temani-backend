package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoListRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

public interface CreateToDoListUseCase {
    ToDoListResponse execute(ToDoListRequest request, UUID userId);
}