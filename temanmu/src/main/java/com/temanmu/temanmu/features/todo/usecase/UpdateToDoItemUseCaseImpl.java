package com.temanmu.temanmu.features.todo.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoItemDtoMapper;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemJpaRepository;
import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoItemRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateToDoItemUseCaseImpl implements UpdateToDoItemUseCase {

    private final ToDoItemJpaRepository toDoItemJpaRepository;
    private final ToDoItemDtoMapper mapper;

    @Override
    public ToDoItemResponse execute(UUID itemId, ToDoItemRequest request, UUID userId) {
        // Fetch the existing item entity
        ToDoItemEntity itemEntity = toDoItemJpaRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("ToDoItem not found"));

        // Check ownership
        if (!itemEntity.getToDoList().getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only update items in your own ToDoLists");
        }

        // Update fields
        itemEntity.setDescription(request.getDescription());
        itemEntity.setUpdatedAt(LocalDateTime.now());

        // Save
        ToDoItemEntity saved = toDoItemJpaRepository.save(itemEntity);

        // Map to DTO
        com.temanmu.temanmu.features.todo.domain.model.ToDoItem toDoItem =
            new com.temanmu.temanmu.features.todo.domain.model.ToDoItem(
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