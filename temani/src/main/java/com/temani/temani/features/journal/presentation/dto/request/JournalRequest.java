package com.temani.temani.features.journal.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalRequest {

	@NotBlank(message = "Title can't be empty!")
	private String title;

	@NotBlank(message = "Content can't be empty!")
	private String content;

}
