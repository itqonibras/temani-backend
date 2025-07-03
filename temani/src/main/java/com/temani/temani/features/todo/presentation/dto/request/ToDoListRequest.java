package com.temani.temani.features.todo.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoListRequest {

	@NotBlank(message = "Title can't be empty!")
	private String title;

	@NotNull(message = "Is shared can't be null!")
	private Boolean isShared;

} 