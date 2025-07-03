package com.temani.temani.features.todo.usecase;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.features.todo.domain.repository.ToDoListRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoListDtoMapper;
import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;

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