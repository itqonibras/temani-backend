package com.temanmu.temanmu.features.todo.usecase;

import java.util.List;
import java.util.UUID;

import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

public interface GetAllToDoListsUseCase {
    List<ToDoListResponse> execute(UUID userId);
}