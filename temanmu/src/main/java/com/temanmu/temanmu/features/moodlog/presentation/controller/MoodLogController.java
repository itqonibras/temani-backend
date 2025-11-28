package com.temanmu.temanmu.features.moodlog.presentation.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.constants.MoodLogMessages;
import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodLogResponse;
import com.temanmu.temanmu.features.moodlog.presentation.dto.response.MoodSummaryResponse;
import com.temanmu.temanmu.features.moodlog.usecase.CreateMoodLogUseCase;
import com.temanmu.temanmu.features.moodlog.usecase.DeleteMoodLogUseCase;
import com.temanmu.temanmu.features.moodlog.usecase.GetAllMoodLogsUseCase;
import com.temanmu.temanmu.features.moodlog.usecase.GetMoodSummaryUseCase;
import com.temanmu.temanmu.features.moodlog.usecase.UpdateMoodLogUseCase;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mood-logs")
public class MoodLogController {

	private final GetAllMoodLogsUseCase getAllMoodLogsUseCase;
	private final CreateMoodLogUseCase createMoodLogUseCase;
	private final UpdateMoodLogUseCase updateMoodLogUseCase;
	private final DeleteMoodLogUseCase deleteMoodLogUseCase;
	private final GetMoodSummaryUseCase getMoodSummaryUseCase;
	private final RelationshipRepository relationshipRepository;

	@GetMapping
	public ResponseEntity<?> getMoodLogs(Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			List<MoodLogResponse> moodLogs = getAllMoodLogsUseCase.execute(user.getId());
			return ResponseEntity.ok(BaseResponse.success(MoodLogMessages.MOOD_LOGS_RECEIVED_SUCCESS, moodLogs));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<?> createMoodLog(@RequestBody @Valid MoodLogRequest request, Authentication auth) {
		if (!request.isMoodVisualValid()) {
			return ResponseEntity.badRequest().body(BaseResponse
					.error("Mood visual must be one of: sangat buruk, buruk, biasa saja, baik, sangat baik"));
		}
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			MoodLogResponse moodLog = createMoodLogUseCase.execute(request, user.getId());
			return ResponseEntity.ok(BaseResponse.success(MoodLogMessages.MOOD_LOG_CREATED_SUCCESS, moodLog));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateMoodLog(@PathVariable UUID id, @RequestBody @Valid MoodLogRequest request,
			Authentication auth) {
		if (!request.isMoodVisualValid()) {
			return ResponseEntity.badRequest().body(BaseResponse
					.error("Mood visual must be one of: sangat buruk, buruk, biasa saja, baik, sangat baik"));
		}
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			MoodLogResponse moodLog = updateMoodLogUseCase.execute(id, request, user.getId());
			return ResponseEntity.ok(BaseResponse.success(MoodLogMessages.MOOD_LOG_UPDATED_SUCCESS, moodLog));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMoodLog(@PathVariable UUID id, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			deleteMoodLogUseCase.execute(id, user.getId());
			return ResponseEntity.ok(BaseResponse.success(String.format(MoodLogMessages.MOOD_LOG_DELETED_SUCCESS, id)));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/summary")
	public ResponseEntity<?> getMoodSummary(
			@RequestParam(required = false) String weekStart,
			Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			LocalDate startDate;
			if (weekStart != null && !weekStart.isEmpty()) {
				startDate = LocalDate.parse(weekStart);
			} else {
				// Default to current week (Monday of current week)
				startDate = LocalDate.now().with(DayOfWeek.MONDAY);
			}

			MoodSummaryResponse summary = getMoodSummaryUseCase.execute(user.getId(), startDate);
			return ResponseEntity.ok(BaseResponse.success("Mood summary retrieved successfully", summary));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@GetMapping("/summary/user/{userId}")
	public ResponseEntity<?> getMoodSummaryByUserId(
			@PathVariable UUID userId,
			@RequestParam(required = false) String weekStart,
			Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User authenticatedUser = userDetails.getUser();
		try {
			// Check if user is requesting their own summary
			if (authenticatedUser.getId().equals(userId)) {
				LocalDate startDate;
				if (weekStart != null && !weekStart.isEmpty()) {
					startDate = LocalDate.parse(weekStart);
				} else {
					// Default to current week (Monday of current week)
					startDate = LocalDate.now().with(DayOfWeek.MONDAY);
				}

				MoodSummaryResponse summary = getMoodSummaryUseCase.execute(userId, startDate);
				return ResponseEntity.ok(BaseResponse.success("Mood summary retrieved successfully", summary));
			}

			// Check if authenticated user is a caregiver and has an accepted relationship with the requested user
			boolean isCaregiver = authenticatedUser.getRoles().stream()
					.anyMatch(r -> r.getName().equalsIgnoreCase("CAREGIVER"));

			if (isCaregiver) {
				// Check if there's an accepted relationship where the caregiver is the authenticated user
				// and the client is the requested user
				var relationship = relationshipRepository.findByClientIdAndCaregiverId(userId, authenticatedUser.getId());
				if (relationship.isPresent() && relationship.get().isAccepted()) {
					LocalDate startDate;
					if (weekStart != null && !weekStart.isEmpty()) {
						startDate = LocalDate.parse(weekStart);
					} else {
						// Default to current week (Monday of current week)
						startDate = LocalDate.now().with(DayOfWeek.MONDAY);
					}

					MoodSummaryResponse summary = getMoodSummaryUseCase.execute(userId, startDate);
					return ResponseEntity.ok(BaseResponse.success("Mood summary retrieved successfully", summary));
				}
			}

			// Access denied - user doesn't have permission to view this user's mood summary
			return ResponseEntity.status(403).body(BaseResponse.error("Access denied: You can only view your own mood summary or summaries of clients you have an accepted relationship with"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}