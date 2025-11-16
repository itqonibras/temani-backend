package com.temani.temani.features.relationship.presentation.dto.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipRequest {

	@NotNull(message = "Target ID is required")
	private UUID targetId;

}
