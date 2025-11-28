package com.temanmu.temanmu.features.todo.infrastructure.mapper;

import org.mapstruct.Mapper;

import com.temanmu.temanmu.features.todo.domain.model.ToDoList;
import com.temanmu.temanmu.features.todo.presentation.dto.response.ToDoListResponse;

@Mapper(componentModel = "spring", uses = {ToDoItemDtoMapper.class})
public interface ToDoListDtoMapper {

	ToDoListResponse toDto(ToDoList toDoList);

}