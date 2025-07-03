package com.temani.temani.features.todo.usecase;

import java.util.UUID;

import com.temani.temani.features.todo.presentation.dto.request.ToDoListRequest;
import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;

public interface CreateToDoListUseCase {
    ToDoListResponse execute(ToDoListRequest request, UUID userId);
} 