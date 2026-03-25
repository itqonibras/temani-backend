package com.temanmu.temanmu.features.todo.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.interactionlog.domain.service.InteractionLogService;
import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoItemDtoMapper;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemJpaRepository;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToggleToDoItemCompleteUseCaseImpl implements ToggleToDoItemCompleteUseCase {

    private final ToDoItemJpaRepository toDoItemJpaRepository;
    private final ToDoItemDtoMapper mapper;
    private final InteractionLogService interactionLogService;

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
        boolean wasComplete = itemEntity.getIsComplete();
        itemEntity.setIsComplete(!itemEntity.getIsComplete());
        itemEntity.setUpdatedAt(LocalDateTime.now());

        // Save
        ToDoItemEntity saved = toDoItemJpaRepository.save(itemEntity);

        // Log interaction only when item is marked as complete
        if (!wasComplete && saved.getIsComplete()) {
            try {
                interactionLogService.logInteraction(
                        userId,
                        "todo",
                        "toggle",
                        "todoitem",
                        saved.getId(),
                        "Tugas Selesai",
                        saved.getDescription());
            } catch (Exception e) {
                System.err.println("Failed to log todo interaction: " + e.getMessage());
            }
        }

        // Map to DTO
        com.temanmu.temanmu.features.todo.domain.model.ToDoItem toDoItem = new com.temanmu.temanmu.features.todo.domain.model.ToDoItem(
                saved.getId(),
                saved.getToDoList().getId(),
                saved.getDescription(),
                saved.getPriority(),
                saved.getIsComplete(),
                saved.getCreatedAt(),
                saved.getUpdatedAt());
        return mapper.toDto(toDoItem);
    }
}