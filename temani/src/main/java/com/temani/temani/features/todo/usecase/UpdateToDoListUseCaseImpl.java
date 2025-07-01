package com.temani.temani.features.todo.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.todo.infrastructure.persistence.ToDoListEntity;
import com.temani.temani.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temani.temani.features.todo.presentation.dto.request.ToDoListRequest;
import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateToDoListUseCaseImpl implements UpdateToDoListUseCase {

    private final ToDoListJpaRepository toDoListJpaRepository;
    private final ToDoListDtoMapper mapper;

    @Override
    public ToDoListResponse execute(UUID listId, ToDoListRequest request, UUID userId) {
        // Fetch the existing entity
        ToDoListEntity entity = toDoListJpaRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("ToDoList not found"));

        // Check ownership
        if (!entity.getUser().getId().equals(userId)) {
            throw new RuntimeException("You can only update your own ToDoLists");
        }

        // Update fields
        entity.setTitle(request.getTitle());
        entity.setIsShared(request.getIsShared());
        entity.setUpdatedAt(java.time.LocalDateTime.now());

        // Save
        ToDoListEntity saved = toDoListJpaRepository.save(entity);

        // Map to DTO
        com.temani.temani.features.todo.domain.model.ToDoList toDoList = new com.temani.temani.features.todo.domain.model.ToDoList(
            saved.getId(),
            saved.getUser().getId(),
            saved.getTitle(),
            saved.getIsShared(),
            saved.getCreatedAt(),
            saved.getUpdatedAt(),
            java.util.Collections.emptyList()
        );
        return mapper.toDto(toDoList);
    }
} 