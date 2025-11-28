package com.temanmu.temanmu.features.todo.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.todo.domain.repository.ToDoListRepository;
import com.temanmu.temanmu.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetAllToDoListsUseCaseImpl implements GetAllToDoListsUseCase {

    private final ToDoListRepository toDoListRepository;
    private final ToDoListDtoMapper mapper;

    @Override
    public List<ToDoListResponse> execute(UUID userId) {
        return toDoListRepository.findAllByUserId(userId)
            .stream().map(mapper::toDto).toList();
    }
}