package com.temani.temani.features.interactionlog.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.interactionlog.presentation.dto.response.InteractionLogResponse;
import com.temani.temani.features.interactionlog.usecase.GetAllInteractionLogsUseCase;
import com.temani.temani.features.interactionlog.usecase.GetInteractionLogsByFeatureUseCase;
import com.temani.temani.features.interactionlog.domain.service.InteractionLogService;
import com.temani.temani.features.profile.domain.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interaction-logs")
public class InteractionLogController {

    private final GetAllInteractionLogsUseCase getAllInteractionLogsUseCase;
    private final GetInteractionLogsByFeatureUseCase getInteractionLogsByFeatureUseCase;
    private final InteractionLogService interactionLogService;

    @GetMapping
    public ResponseEntity<?> getInteractionLogs(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            System.out.println("Fetching interaction logs for user: " + user.getId());
            List<InteractionLogResponse> interactionLogs = getAllInteractionLogsUseCase.execute(user.getId());
            System.out.println("Found " + interactionLogs.size() + " interaction logs");
            return ResponseEntity.ok(BaseResponse.success("Interaction logs retrieved successfully", interactionLogs));
        } catch (Exception e) {
            System.err.println("Error fetching interaction logs: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/feature/{feature}")
    public ResponseEntity<?> getInteractionLogsByFeature(@PathVariable String feature, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            List<InteractionLogResponse> interactionLogs = getInteractionLogsByFeatureUseCase.execute(user.getId(),
                    feature);
            return ResponseEntity.ok(BaseResponse
                    .success("Interaction logs for feature " + feature + " retrieved successfully", interactionLogs));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }

    // Test endpoint to manually create an interaction log
    @PostMapping("/test")
    public ResponseEntity<?> createTestLog(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            interactionLogService.logInteraction(
                    user.getId(),
                    "test",
                    "create",
                    "test",
                    UUID.randomUUID(),
                    "Test Interaction",
                    "This is a test interaction log");
            return ResponseEntity.ok(BaseResponse.success("Test interaction log created successfully"));
        } catch (Exception e) {
            System.err.println("Error creating test log: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}