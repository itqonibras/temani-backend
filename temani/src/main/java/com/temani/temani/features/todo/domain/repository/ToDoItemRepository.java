package com.temani.temani.features.todo.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.temani.temani.features.todo.domain.model.ToDoItem;

public interface ToDoItemRepository {

	ToDoItem save(ToDoItem toDoItem);

	void delete(ToDoItem toDoItem);

	Optional<ToDoItem> findById(UUID id);

	List<ToDoItem> findAllByToDoListId(UUID toDoListId);

	List<ToDoItem> findAllByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime start, LocalDateTime end);

	long countByUserIdAndCreatedAtBetween(UUID userId, LocalDateTime start, LocalDateTime end);

}