package com.temanmu.temanmu.features.todo.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoItemDtoMapper;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemJpaRepository;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateToDoItemUseCaseImpl implements CreateToDoItemUseCase {

    private static final String DEFAULT_PRIORITY = "MEDIUM";

    private final ToDoItemJpaRepository toDoItemJpaRepository;
    private final ToDoListJpaRepository toDoListJpaRepository;
    private final ToDoItemDtoMapper mapper;

    @Override
    public ToDoItemResponse execute(UUID listId, ToDoItemRequest request, UUID userId) {
        // Fetch the parent list entity
        ToDoListEntity toDoListEntity = toDoListJpaRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("ToDoList not found"));

        // Check ownership
        if (!toDoListEntity.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only add items to your own ToDoLists");
        }

        // Create the item entity
        ToDoItemEntity itemEntity = new ToDoItemEntity();
        itemEntity.setToDoList(toDoListEntity);
        itemEntity.setDescription(request.getDescription());
        itemEntity.setPriority(normalizePriority(request.getPriority()));
        itemEntity.setScheduledAt(request.getScheduledAt());
        itemEntity.setIsComplete(false);
        itemEntity.setCreatedAt(LocalDateTime.now());
        itemEntity.setUpdatedAt(LocalDateTime.now());

        // Save
        ToDoItemEntity saved = toDoItemJpaRepository.save(itemEntity);

        // Map to DTO
        com.temanmu.temanmu.features.todo.domain.model.ToDoItem toDoItem =
            new com.temanmu.temanmu.features.todo.domain.model.ToDoItem(
                saved.getId(),
                saved.getToDoList().getId(),
                saved.getDescription(),
                saved.getPriority(),
                saved.getIsComplete(),
                saved.getScheduledAt(),
                saved.getCreatedAt(),
                saved.getUpdatedAt()
            );
        return mapper.toDto(toDoItem);
    }

    private String normalizePriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return DEFAULT_PRIORITY;
        }

        String normalized = priority.trim().toUpperCase();
        if (normalized.equals("HIGH") || normalized.equals("MEDIUM") || normalized.equals("LOW")) {
            return normalized;
        }
        return DEFAULT_PRIORITY;
    }
}