package com.temani.temani.features.relationship.presentation.dto.request;

import com.temani.temani.common.enums.RelationshipAction;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRelationshipStatusRequest {

	@NotNull(message = "Status can't be empty!")
	private RelationshipAction status;

}
