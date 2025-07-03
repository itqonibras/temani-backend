package com.temani.temani.features.todo.infrastructure.persistence;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoListJpaRepository extends JpaRepository<ToDoListEntity, UUID> {

	List<ToDoListEntity> findAllByUserIdOrderByCreatedAtDesc(UUID userId);

} 