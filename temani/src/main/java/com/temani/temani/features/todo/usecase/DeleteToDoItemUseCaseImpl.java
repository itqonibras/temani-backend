package com.temani.temani.features.todo.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.todo.infrastructure.persistence.ToDoItemEntity;
import com.temani.temani.features.todo.infrastructure.persistence.ToDoItemJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteToDoItemUseCaseImpl implements DeleteToDoItemUseCase {

    private final ToDoItemJpaRepository toDoItemJpaRepository;

    @Override
    public void execute(UUID itemId, UUID userId) {
        ToDoItemEntity itemEntity = toDoItemJpaRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("ToDoItem not found"));

        // Check ownership
        if (!itemEntity.getToDoList().getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only delete items from your own ToDoLists");
        }

        toDoItemJpaRepository.delete(itemEntity);
    }
} 