package com.temani.temani.features.relationship.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.constants.RelationshipMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.relationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.relationship.presentation.dto.request.UpdateRelationshipStatusRequest;
import com.temani.temani.features.relationship.presentation.dto.response.PotentialRelationshipResponse;
import com.temani.temani.features.relationship.presentation.dto.response.RelationshipResponse;
import com.temani.temani.features.relationship.usecase.AcceptRelationshipUseCase;
import com.temani.temani.features.relationship.usecase.CancelRelationshipUseCase;
import com.temani.temani.features.relationship.usecase.CreateRelationshipUseCase;
import com.temani.temani.features.relationship.usecase.DeleteRelationshipUseCase;
import com.temani.temani.features.relationship.usecase.FindPotentialRelationshipUseCase;
import com.temani.temani.features.relationship.usecase.FindAcceptedRelationshipsUseCase;
import com.temani.temani.features.relationship.usecase.FindPendingReceivedRelationshipsUseCase;
import com.temani.temani.features.relationship.usecase.FindPendingSentRelationshipsUseCase;
import com.temani.temani.features.relationship.usecase.RejectRelationshipUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relationships")
public class RelationshipController {

	private final CreateRelationshipUseCase createRelationshipUseCase;

	private final FindAcceptedRelationshipsUseCase findAcceptedRelationshipsUseCase;

	private final FindPendingSentRelationshipsUseCase findPendingSentRelationshipsUseCase;

	private final FindPendingReceivedRelationshipsUseCase findPendingReceivedRelationshipsUseCase;

	private final AcceptRelationshipUseCase acceptRelationshipUseCase;

	private final RejectRelationshipUseCase rejectRelationshipUseCase;

	private final CancelRelationshipUseCase cancelRelationshipUseCase;

	private final FindPotentialRelationshipUseCase findPotentialRelationshipUseCase;

	private final DeleteRelationshipUseCase deleteRelationshipUseCase;

	@PostMapping
	public ResponseEntity<?> createRelationship(@RequestBody RelationshipRequest request, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			RelationshipResponse relationship = createRelationshipUseCase.execute(request, user.getId(),
					user.getRoles());
			return ResponseEntity.ok(BaseResponse.success(RelationshipMessages.RELATIONSHIP_CREATED_SUCCESS, relationship));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<?> getRelationships(@RequestParam(defaultValue = "accepted") String status,
			@RequestParam(required = false) String direction, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();

		try {
			List<RelationshipResponse> relationships = switch (status.toLowerCase()) {
				case "accepted" -> {
					yield findAcceptedRelationshipsUseCase.execute(user.getId());
				}
				case "pending" -> {
					if ("sent".equalsIgnoreCase(direction)) {
						yield findPendingSentRelationshipsUseCase.execute(user.getId());
					}
					else if ("received".equalsIgnoreCase(direction)) {
						yield findPendingReceivedRelationshipsUseCase.execute(user.getId());
					}
					else {
						throw new IllegalArgumentException(RelationshipMessages.INVALID_PENDING_DIRECTION);
					}
				}
				default -> {
					throw new IllegalArgumentException(RelationshipMessages.INVALID_STATUS_PARAMETER);
				}
			};

			return ResponseEntity.ok(BaseResponse.success(RelationshipMessages.RELATIONSHIPS_RECEIVED_SUCCESS, relationships));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> acceptRelationship(@PathVariable UUID id,
			@RequestBody @Valid UpdateRelationshipStatusRequest request, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();

		try {
			RelationshipResponse relationship = null;
			String message;

			switch (request.getStatus().toLowerCase()) {
				case "accept" -> {
					relationship = acceptRelationshipUseCase.execute(request, id, user);
					message = RelationshipMessages.RELATIONSHIP_UPDATED_SUCCESS;
					return ResponseEntity.ok(BaseResponse.success(message, relationship));
				}
				case "reject" -> {
					rejectRelationshipUseCase.execute(request, id, user);
					message = String.format(RelationshipMessages.RELATIONSHIP_REJECTED_SUCCESS, id);
					return ResponseEntity.ok(BaseResponse.success(message, null));
				}
				case "cancel" -> {
					cancelRelationshipUseCase.execute(request, id, user);
					message = String.format(RelationshipMessages.RELATIONSHIP_CANCELED_SUCCESS, id);
					return ResponseEntity.ok(BaseResponse.success(message, null));
				}
				default -> throw new IllegalArgumentException(String.format(RelationshipMessages.INVALID_STATUS, request.getStatus()));
			}
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchPotentialRelationship(@RequestParam String role,
			@RequestParam(required = false) String keyword, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			List<PotentialRelationshipResponse> results = findPotentialRelationshipUseCase.execute(role, keyword,
					user.getId());
			return ResponseEntity.ok(BaseResponse.success(RelationshipMessages.USERS_RECEIVED_SUCCESS, results));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRelationship(@PathVariable UUID id, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			deleteRelationshipUseCase.execute(id, user);
			return ResponseEntity
				.ok(BaseResponse.success(String.format(RelationshipMessages.RELATIONSHIP_DELETED_SUCCESS, id)));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}
