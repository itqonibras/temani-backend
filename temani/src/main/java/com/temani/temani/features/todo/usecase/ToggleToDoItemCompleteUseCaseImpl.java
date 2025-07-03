package com.temani.temani.features.todo.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.todo.infrastructure.persistence.ToDoItemEntity;
import com.temani.temani.features.todo.infrastructure.persistence.ToDoItemJpaRepository;
import com.temani.temani.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoItemDtoMapper;
import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToggleToDoItemCompleteUseCaseImpl implements ToggleToDoItemCompleteUseCase {

    private final ToDoItemJpaRepository toDoItemJpaRepository;
    private final ToDoListJpaRepository toDoListJpaRepository;
    private final ToDoItemDtoMapper mapper;

    @Override
    public ToDoItemResponse execute(UUID itemId, UUID userId) {
        // Fetch the existing item entity
        ToDoItemEntity itemEntity = toDoItemJpaRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("ToDoItem not found"));

        // Check ownership
        if (!itemEntity.getToDoList().getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only toggle items in your own ToDoLists");
        }

        // Toggle the completion status
        itemEntity.setIsComplete(!itemEntity.getIsComplete());
        itemEntity.setUpdatedAt(LocalDateTime.now());

        // Save
        ToDoItemEntity saved = toDoItemJpaRepository.save(itemEntity);

        // Map to DTO
        com.temani.temani.features.todo.domain.model.ToDoItem toDoItem =
            new com.temani.temani.features.todo.domain.model.ToDoItem(
                saved.getId(),
                saved.getToDoList().getId(),
                saved.getDescription(),
                saved.getIsComplete(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
            );
        return mapper.toDto(toDoItem);
    }
} 