package com.temani.temani.features.todo.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.temani.temani.features.todo.domain.model.ToDoItem;
import com.temani.temani.features.todo.domain.repository.ToDoItemRepository;
import com.temani.temani.features.todo.infrastructure.mapper.ToDoItemEntityMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ToDoItemRepositoryImpl implements ToDoItemRepository {

    private final ToDoItemJpaRepository jpaRepository;
    private final ToDoItemEntityMapper mapper;

    @Override
    public ToDoItem save(ToDoItem toDoItem) {
        var entity = mapper.toEntity(toDoItem);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void delete(ToDoItem toDoItem) {
        var entity = mapper.toEntity(toDoItem);
        jpaRepository.delete(entity);
    }

    @Override
    public Optional<ToDoItem> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ToDoItem> findAllByToDoListId(UUID toDoListId) {
        return jpaRepository.findAllByToDoListIdOrderByCreatedAtAsc(toDoListId)
            .stream().map(mapper::toDomain).toList();
    }
} 