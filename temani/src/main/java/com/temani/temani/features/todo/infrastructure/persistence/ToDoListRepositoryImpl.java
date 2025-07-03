package com.temani.temani.features.todo.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.todo.domain.model.ToDoList;
import com.temani.temani.features.todo.domain.repository.ToDoListRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoListEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ToDoListRepositoryImpl implements ToDoListRepository {

	private final ToDoListJpaRepository jpaRepository;
	private final ToDoListEntityMapper mapper;

	@Override
	public ToDoList save(ToDoList toDoList) {
		ToDoListEntity entity = mapper.toEntity(toDoList);
		ToDoListEntity savedEntity = jpaRepository.save(entity);
		return mapper.toDomain(savedEntity);
	}

	@Override
	public void delete(ToDoList toDoList) {
		ToDoListEntity entity = mapper.toEntity(toDoList);
		jpaRepository.delete(entity);
	}

	@Override
	public Optional<ToDoList> findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain);
	}

	@Override
	public List<ToDoList> findAllByUserId(UUID userId) {
		List<ToDoListEntity> entities = jpaRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
		return entities.stream().map(mapper::toDomain).toList();
	}

} 