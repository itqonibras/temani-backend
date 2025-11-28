package com.temanmu.temanmu.features.todo.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.features.todo.domain.model.ToDoList;
import com.temanmu.temanmu.features.todo.domain.repository.ToDoListRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeleteToDoListUseCaseImpl implements DeleteToDoListUseCase {

    private final ToDoListRepository toDoListRepository;

    @Override
    public void execute(UUID listId, UUID userId) {
        ToDoList toDoList = toDoListRepository.findById(listId)
            .orElseThrow(() -> new RuntimeException("ToDoList not found"));
        if (!toDoList.getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own ToDoLists");
        }
        toDoListRepository.delete(toDoList);
    }
}