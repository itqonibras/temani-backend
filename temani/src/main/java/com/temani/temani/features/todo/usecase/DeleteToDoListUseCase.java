package com.temani.temani.features.todo.usecase;

import java.util.UUID;

public interface DeleteToDoListUseCase {
    void execute(UUID listId, UUID userId);
} 