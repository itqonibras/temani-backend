package com.temani.temani.features.todo.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoItemJpaRepository extends JpaRepository<ToDoItemEntity, UUID> {

	List<ToDoItemEntity> findAllByToDoListIdOrderByCreatedAtAsc(UUID toDoListId);

} 