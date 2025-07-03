package com.temani.temani.features.todo.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.todo.domain.model.ToDoList;

public interface ToDoListRepository {

	ToDoList save(ToDoList toDoList);

	void delete(ToDoList toDoList);

	Optional<ToDoList> findById(UUID id);

	List<ToDoList> findAllByUserId(UUID userId);

} 