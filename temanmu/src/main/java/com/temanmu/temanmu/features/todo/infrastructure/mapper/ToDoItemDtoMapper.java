package com.temanmu.temanmu.features.todo.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.todo.domain.model.ToDoItem;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoItemResponse;

@Mapper(componentModel = "spring")
public interface ToDoItemDtoMapper {

	ToDoItemResponse toDto(ToDoItem toDoItem);

}