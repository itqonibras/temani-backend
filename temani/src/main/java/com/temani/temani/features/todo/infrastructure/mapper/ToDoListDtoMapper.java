package com.temani.temani.features.todo.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temani.temani.features.todo.domain.model.ToDoList;
import com.temani.temani.features.todo.presentation.dto.response.ToDoListResponse;

@Mapper(componentModel = "spring", uses = {ToDoItemDtoMapper.class})
public interface ToDoListDtoMapper {

	ToDoListResponse toDto(ToDoList toDoList);

} 