package com.temanmu.temanmu.features.interactionlog.presentation.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.interactionlog.domain.service.InteractionLogService;
import com.temanmu.temanmu.features.interactionlog.presentation.dto.response.InteractionLogResponse;
import com.temanmu.temanmu.features.interactionlog.usecase.GetAllInteractionLogsUseCase;
import com.temanmu.temanmu.features.interactionlog.usecase.GetInteractionLogsByFeatureUseCase;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.relationship.domain.repository.RelationshipRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interaction-logs")
public class InteractionLogController {

    private final GetAllInteractionLogsUseCase getAllInteractionLogsUseCase;
    private final GetInteractionLogsByFeatureUseCase getInteractionLogsByFeatureUseCase;
    private final InteractionLogService interactionLogService;
    private final RelationshipRepository relationshipRepository;

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

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getInteractionLogsByUserId(@PathVariable UUID userId, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User authenticatedUser = userDetails.getUser();
        try {
            // Check if user is requesting their own logs
            if (authenticatedUser.getId().equals(userId)) {
                List<InteractionLogResponse> interactionLogs = getAllInteractionLogsUseCase.execute(userId);
                return ResponseEntity.ok(BaseResponse.success("Interaction logs retrieved successfully", interactionLogs));
            }

            // Check if authenticated user is a caregiver and has an accepted relationship with the requested user
            boolean isCaregiver = authenticatedUser.getRoles().stream()
                    .anyMatch(r -> r.getName().equalsIgnoreCase("CAREGIVER"));

            if (isCaregiver) {
                // Check if there's an accepted relationship where the caregiver is the authenticated user
                // and the client is the requested user
                var relationship = relationshipRepository.findByClientIdAndCaregiverId(userId, authenticatedUser.getId());
                if (relationship.isPresent() && relationship.get().isAccepted()) {
                    List<InteractionLogResponse> interactionLogs = getAllInteractionLogsUseCase.execute(userId);
                    return ResponseEntity.ok(BaseResponse.success("Interaction logs retrieved successfully", interactionLogs));
                }
            }

            // Access denied - user doesn't have permission to view this user's logs
            return ResponseEntity.status(403).body(BaseResponse.error("Access denied: You can only view your own interaction logs or logs of clients you have an accepted relationship with"));
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