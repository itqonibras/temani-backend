package com.temani.temani.features.todo.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.todo.domain.model.ToDoItem;
import com.temani.temani.features.todo.presentation.dto.response.ToDoItemResponse;

@Mapper(componentModel = "spring")
public interface ToDoItemDtoMapper {

	ToDoItemResponse toDto(ToDoItem toDoItem);

} 