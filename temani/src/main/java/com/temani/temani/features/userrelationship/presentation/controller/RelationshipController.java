package com.temani.temani.features.userrelationship.presentation.controller;

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

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.userrelationship.presentation.dto.request.UpdateRelationshipStatusRequest;
import com.temani.temani.features.userrelationship.presentation.dto.response.PotentialRelationshipResponse;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;
import com.temani.temani.features.userrelationship.usecase.AcceptRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.CancelRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.CreateRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.DeleteRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.FindPotentialRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.GetAcceptedRelationshipsUseCase;
import com.temani.temani.features.userrelationship.usecase.GetPendingReceivedRelationshipsUseCase;
import com.temani.temani.features.userrelationship.usecase.GetPendingSentRelationshipsUseCase;
import com.temani.temani.features.userrelationship.usecase.RejectRelationshipUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relationships")
public class RelationshipController {

	private final CreateRelationshipUseCase createRelationshipUseCase;

	private final GetAcceptedRelationshipsUseCase getAcceptedRelationshipsUseCase;

	private final GetPendingSentRelationshipsUseCase getPendingSentRelationshipsUseCase;

	private final GetPendingReceivedRelationshipsUseCase getPendingReceivedRelationshipsUseCase;

	private final AcceptRelationshipUseCase acceptRelationshipUseCase;

	private final RejectRelationshipUseCase rejectRelationshipUseCase;

	private final CancelRelationshipUseCase cancelRelationshipUseCase;

	private final FindPotentialRelationshipUseCase findPotentialRelationshipUseCase;

	private final DeleteRelationshipUseCase deleteRelationshipUseCase;

	@PostMapping("")
	public ResponseEntity<?> createRelationship(@RequestBody RelationshipRequest request, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			RelationshipResponse relationship = createRelationshipUseCase.execute(request, user.getId(),
					user.getRoles());
			return ResponseEntity.ok(BaseResponse.success("Relationship created successfully!", relationship));
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
					yield getAcceptedRelationshipsUseCase.execute(user.getId());
				}
				case "pending" -> {
					if ("sent".equalsIgnoreCase(direction)) {
						yield getPendingSentRelationshipsUseCase.execute(user.getId());
					}
					else if ("received".equalsIgnoreCase(direction)) {
						yield getPendingReceivedRelationshipsUseCase.execute(user.getId());
					}
					else {
						throw new IllegalArgumentException(
								"When status is 'pending', direction must be 'sent' or 'received'");
					}
				}
				default -> {
					throw new IllegalArgumentException("Status must be 'accepted' or 'pending'");
				}
			};

			return ResponseEntity.ok(BaseResponse.success("Relationships received successfully!", relationships));
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
					message = "Relationship updated successfully!";
					return ResponseEntity.ok(BaseResponse.success(message, relationship));
				}
				case "reject" -> {
					rejectRelationshipUseCase.execute(request, id, user);
					message = String.format("Relationship with id %s rejected successfully!", id);
					return ResponseEntity.ok(BaseResponse.success(message, null));
				}
				case "cancel" -> {
					cancelRelationshipUseCase.execute(request, id, user);
					message = String.format("Relationship with id %s canceled successfully!", id);
					return ResponseEntity.ok(BaseResponse.success(message, null));
				}
				default -> throw new IllegalArgumentException("Invalid status: " + request.getStatus());
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
			return ResponseEntity.ok(BaseResponse.success("Users fetched successfully!", results));
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
				.ok(BaseResponse.success(String.format("Relationship with id %s deleted successfully!", id)));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}
