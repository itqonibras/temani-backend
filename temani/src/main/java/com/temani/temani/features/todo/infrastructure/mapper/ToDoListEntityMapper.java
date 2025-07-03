package com.temani.temani.features.todo.infrastructure.mapper;

import org.mapstruct.Mapping;

import com.temani.temani.features.todo.domain.model.ToDoList;
import com.temani.temani.features.todo.infrastructure.persistence.ToDoListEntity;

@org.mapstruct.Mapper(componentModel = "spring", uses = {ToDoItemEntityMapper.class})
public interface ToDoListEntityMapper {

	@Mapping(source = "user.id", target = "userId")
	ToDoList toDomain(ToDoListEntity entity);

	@Mapping(target = "user", ignore = true)
	ToDoListEntity toEntity(ToDoList domain);

} 