package com.temani.temani.features.todo.usecase;

import java.util.List;
import java.util.UUID;

import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;

public interface GetAllToDoListsUseCase {
    List<ToDoListResponse> execute(UUID userId);
} 