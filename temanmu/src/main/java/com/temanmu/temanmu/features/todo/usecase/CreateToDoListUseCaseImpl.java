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

    private static final String FALLBACK_COLOR = "#51A2FF";
    private static final String FALLBACK_ICON = "clipboard";

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
        toDoListEntity.setColorHex(normalizeColor(request.getColorHex()));
        toDoListEntity.setIconName(normalizeIcon(request.getIconName()));
        toDoListEntity.setIsDefault(false);
        toDoListEntity.setSortOrder(1000);
        toDoListEntity.setCreatedAt(LocalDateTime.now());
        toDoListEntity.setUpdatedAt(LocalDateTime.now());

        ToDoListEntity savedEntity = toDoListJpaRepository.save(toDoListEntity);

        // Convert to domain model and then to DTO
        ToDoList toDoList = new ToDoList(
            savedEntity.getId(),
            savedEntity.getUser().getId(),
            savedEntity.getTitle(),
            savedEntity.getIsShared(),
            savedEntity.getColorHex(),
            savedEntity.getIconName(),
            savedEntity.getIsDefault(),
            savedEntity.getSortOrder(),
            savedEntity.getCreatedAt(),
            savedEntity.getUpdatedAt(),
            java.util.Collections.emptyList()
        );

        return mapper.toDto(toDoList);
    }

    private String normalizeColor(String colorHex) {
        if (colorHex == null || colorHex.isBlank()) {
            return FALLBACK_COLOR;
        }
        return colorHex.trim();
    }

    private String normalizeIcon(String iconName) {
        if (iconName == null || iconName.isBlank()) {
            return FALLBACK_ICON;
        }
        return iconName.trim().toLowerCase();
    }
}