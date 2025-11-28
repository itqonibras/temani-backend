package com.temanmu.temanmu.features.todo.usecase;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temanmu.temanmu.features.todo.domain.model.ToDoList;
import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoListRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateToDoListUseCaseImpl implements CreateToDoListUseCase {

    private final ToDoListDtoMapper mapper;
    private final ToDoListJpaRepository toDoListJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public ToDoListResponse execute(ToDoListRequest request, UUID userId) {
        // Get the user entity
        var userEntity = userJpaRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Create the todo list entity directly
        ToDoListEntity toDoListEntity = new ToDoListEntity();
        toDoListEntity.setUser(userEntity);
        toDoListEntity.setTitle(request.getTitle());
        toDoListEntity.setIsShared(request.getIsShared());
        toDoListEntity.setCreatedAt(LocalDateTime.now());
        toDoListEntity.setUpdatedAt(LocalDateTime.now());

        ToDoListEntity savedEntity = toDoListJpaRepository.save(toDoListEntity);

        // Convert to domain model and then to DTO
        ToDoList toDoList = new ToDoList(
            savedEntity.getId(),
            savedEntity.getUser().getId(),
            savedEntity.getTitle(),
            savedEntity.getIsShared(),
            savedEntity.getCreatedAt(),
            savedEntity.getUpdatedAt(),
            java.util.Collections.emptyList()
        );

        return mapper.toDto(toDoList);
    }
}