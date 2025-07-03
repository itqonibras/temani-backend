package com.temani.temani.features.todo.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToDoItemRequest {

	@NotBlank(message = "Description can't be empty!")
	private String description;

} 