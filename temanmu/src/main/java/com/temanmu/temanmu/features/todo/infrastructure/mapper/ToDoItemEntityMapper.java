package com.temanmu.temanmu.features.todo.infrastructure.mapper;

import org.mapstruct.Mapping;

import com.temanmu.temanmu.features.todo.domain.model.ToDoItem;
import com.temanmu.temanmu.features.todo.infrastructure.persistence.ToDoItemEntity;

@org.mapstruct.Mapper(componentModel = "spring")
public interface ToDoItemEntityMapper {

	@Mapping(source = "toDoList.id", target = "toDoListId")
	ToDoItem toDomain(ToDoItemEntity entity);

	@Mapping(target = "toDoList", ignore = true)
	ToDoItemEntity toEntity(ToDoItem domain);

}