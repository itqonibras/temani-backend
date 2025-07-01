package com.temani.temani.features.moodlog.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.constants.MoodLogMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.moodlog.presentation.dto.request.MoodLogRequest;
import com.temani.temani.features.moodlog.presentation.dto.response.MoodLogResponse;
import com.temani.temani.features.moodlog.usecase.CreateMoodLogUseCase;
import com.temani.temani.features.moodlog.usecase.DeleteMoodLogUseCase;
import com.temani.temani.features.moodlog.usecase.GetAllMoodLogsUseCase;
import com.temani.temani.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mood-logs")
public class MoodLogController {

	private final GetAllMoodLogsUseCase getAllMoodLogsUseCase;
	private final CreateMoodLogUseCase createMoodLogUseCase;
	private final DeleteMoodLogUseCase deleteMoodLogUseCase;

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
			return ResponseEntity.badRequest().body(BaseResponse.error("Mood visual must be one of: sangat buruk, buruk, biasa saja, baik, sangat baik"));
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

} 