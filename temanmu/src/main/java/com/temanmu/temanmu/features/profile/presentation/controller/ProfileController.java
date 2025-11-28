package com.temanmu.temanmu.features.profile.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temanmu.temanmu.common.constants.ProfileMessages;
import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.presentation.dto.request.UpdateProfileRequest;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;
import com.temanmu.temanmu.features.profile.usecase.GetProfileUseCase;
import com.temanmu.temanmu.features.profile.usecase.UpdateProfileUseCase;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

	private final GetProfileUseCase getProfileUseCase;
	private final UpdateProfileUseCase updateProfileUseCase;
	private final ObjectMapper objectMapper;
	private final Validator validator;

	@GetMapping("/me")
	public ResponseEntity<?> getProfile(Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			UserResponse profile = getProfileUseCase.execute(user.getId());
			return ResponseEntity.ok(BaseResponse.success(ProfileMessages.PROFILE_RECEIVED_SUCCESS, profile));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PutMapping(value = "/me", consumes = { "multipart/form-data" })
	public ResponseEntity<?> updateProfile(
			@RequestPart(value = "profile", required = false) String profileJson,
			@RequestPart(value = "profilePicture", required = false) MultipartFile profilePicture,
			Authentication auth) {
		// Check authentication
		if (auth == null || auth.getPrincipal() == null) {
			return ResponseEntity.status(401).body(BaseResponse.error("Unauthorized: Authentication required"));
		}

		try {
			CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
			User user = userDetails.getUser();

			// Check if at least one update is being made
			if (profileJson == null && (profilePicture == null || profilePicture.isEmpty())) {
				return ResponseEntity.badRequest().body(BaseResponse.error("At least one field (profile data or profile picture) must be provided for update"));
			}

			UpdateProfileRequest request = null;
			if (profileJson != null && !profileJson.trim().isEmpty()) {
				try {
					request = objectMapper.readValue(profileJson, UpdateProfileRequest.class);

					// Validate only non-null fields
					Set<ConstraintViolation<UpdateProfileRequest>> violations = validator.validate(request);
					if (!violations.isEmpty()) {
						String errorMessage = violations.stream()
								.map(ConstraintViolation::getMessage)
								.collect(Collectors.joining(", "));
						return ResponseEntity.badRequest().body(BaseResponse.error(errorMessage));
					}
				}
				catch (com.fasterxml.jackson.core.JsonProcessingException e) {
					return ResponseEntity.badRequest().body(BaseResponse.error("Invalid JSON format in profile data: " + e.getMessage()));
				}
			}

			UserResponse updatedProfile = updateProfileUseCase.execute(user.getId(), request, profilePicture);
			return ResponseEntity.ok(BaseResponse.success(ProfileMessages.PROFILE_UPDATED_SUCCESS, updatedProfile));
		}
		catch (ClassCastException e) {
			return ResponseEntity.status(401).body(BaseResponse.error("Unauthorized: Invalid authentication"));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}

