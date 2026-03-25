package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temanmu.temanmu.features.todo.presentation.dto.request.ToDoListRequest;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateToDoListUseCaseImpl implements UpdateToDoListUseCase {

    private static final String FALLBACK_COLOR = "#51A2FF";
    private static final String FALLBACK_ICON = "clipboard";

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
        entity.setColorHex(normalizeColor(request.getColorHex(), entity.getColorHex()));
        entity.setIconName(normalizeIcon(request.getIconName(), entity.getIconName()));
        entity.setUpdatedAt(java.time.LocalDateTime.now());

        // Save
        ToDoListEntity saved = toDoListJpaRepository.save(entity);

        // Map to DTO
        com.temanmu.temanmu.features.todo.domain.model.ToDoList toDoList = new com.temanmu.temanmu.features.todo.domain.model.ToDoList(
            saved.getId(),
            saved.getUser().getId(),
            saved.getTitle(),
            saved.getIsShared(),
            saved.getColorHex(),
            saved.getIconName(),
            saved.getIsDefault(),
            saved.getSortOrder(),
            saved.getCreatedAt(),
            saved.getUpdatedAt(),
            java.util.Collections.emptyList()
        );
        return mapper.toDto(toDoList);
    }

    private String normalizeColor(String newColor, String currentColor) {
        if (newColor != null && !newColor.isBlank()) {
            return newColor.trim();
        }
        if (currentColor != null && !currentColor.isBlank()) {
            return currentColor;
        }
        return FALLBACK_COLOR;
    }

    private String normalizeIcon(String newIcon, String currentIcon) {
        if (newIcon != null && !newIcon.isBlank()) {
            return newIcon.trim().toLowerCase();
        }
        if (currentIcon != null && !currentIcon.isBlank()) {
            return currentIcon;
        }
        return FALLBACK_ICON;
    }
}