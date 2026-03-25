package com.temanmu.temanmu.features.todo.usecase;

import java.util.List;
import java.util.UUID;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.profile.infrastructure.persistence.UserJpaRepository;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListEntity;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoListJpaRepository;
import com.temanmu.temanmu.features.todo.domain.repository.ToDoListRepository;
import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllToDoListsUseCaseImpl implements GetAllToDoListsUseCase {

    private static final List<DefaultGroup> DEFAULT_GROUPS = List.of(
            new DefaultGroup(0, "Tugas Univ", "university", "#57A9F5"),
            new DefaultGroup(1, "Makan", "apple", "#7E82F8"),
            new DefaultGroup(2, "Olahraga", "basketball", "#F6A425"),
            new DefaultGroup(3, "Baca", "book", "#83C337"));

    private final ToDoListRepository toDoListRepository;
    private final ToDoListDtoMapper mapper;
    private final ToDoListJpaRepository toDoListJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<ToDoListResponse> execute(UUID userId) {
        ensureDefaultGroups(userId);

        return toDoListRepository.findAllByUserId(userId)
            .stream().map(mapper::toDto).toList();
    }

    private void ensureDefaultGroups(UUID userId) {
        var existing = toDoListJpaRepository.findAllByUserIdOrderBySortOrderAscCreatedAtDesc(userId);
        Set<Integer> existingDefaultOrders = existing.stream()
                .filter(list -> Boolean.TRUE.equals(list.getIsDefault()) && list.getSortOrder() != null)
                .map(ToDoListEntity::getSortOrder)
                .collect(java.util.stream.Collectors.toSet());

        if (existingDefaultOrders.size() >= DEFAULT_GROUPS.size()) {
            return;
        }

        var user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        for (DefaultGroup group : DEFAULT_GROUPS) {
            if (existingDefaultOrders.contains(group.sortOrder())) {
                continue;
            }

            ToDoListEntity entity = new ToDoListEntity();
            entity.setUser(user);
            entity.setTitle(group.title());
            entity.setIsShared(false);
            entity.setColorHex(group.colorHex());
            entity.setIconName(group.iconName());
            entity.setIsDefault(true);
            entity.setSortOrder(group.sortOrder());
            toDoListJpaRepository.save(entity);
        }
    }

    private record DefaultGroup(int sortOrder, String title, String iconName, String colorHex) {
    }
}