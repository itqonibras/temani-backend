package com.temani.temani.features.journal.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalResponse {

	private UUID id;

	private UUID userId;

	private String title;

	private String content;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
