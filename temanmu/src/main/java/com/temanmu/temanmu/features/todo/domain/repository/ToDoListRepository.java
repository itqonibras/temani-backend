package com.temanmu.temanmu.features.todo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temanmu.temanmu.features.todo.domain.model.ToDoList;

public interface ToDoListRepository {

	ToDoList save(ToDoList toDoList);

	void delete(ToDoList toDoList);

	Optional<ToDoList> findById(UUID id);

	List<ToDoList> findAllByUserId(UUID userId);

}