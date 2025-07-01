package com.temani.temani.features.todo.usecase;

import java.util.UUID;

public interface DeleteToDoItemUseCase {
    void execute(UUID itemId, UUID userId);
} 