package com.temani.temani.features.userrelationship.presentation.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;
import com.temani.temani.features.userrelationship.usecase.CreateRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.GetAcceptedRelationshipsUseCase;
import com.temani.temani.features.userrelationship.usecase.GetPendingReceivedRelationshipsUseCase;
import com.temani.temani.features.userrelationship.usecase.GetPendingSentRelationshipsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relationships")
public class RelationshipController {

    private final CreateRelationshipUseCase createRelationshipUseCase;
    private final GetAcceptedRelationshipsUseCase getAcceptedRelationshipsUseCase;
    private final GetPendingSentRelationshipsUseCase getPendingSentRelationshipsUseCase;
    private final GetPendingReceivedRelationshipsUseCase getPendingReceivedRelationshipsUseCase;

    @PostMapping("")
    public ResponseEntity<?> createRelationship(@RequestBody RelationshipRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            RelationshipResponse relationship = createRelationshipUseCase.execute(request, user.getId(),
                    user.getRoles());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Relationship created successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(relationship);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } catch (IllegalStateException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<?> getRelationships(
            @RequestParam(defaultValue = "accepted") String status,
            @RequestParam(required = false) String direction,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            List<RelationshipResponse> relationships = switch (status.toLowerCase()) {
                case "accepted" -> getAcceptedRelationshipsUseCase.execute(user.getId());
                case "pending" -> {
                    if ("sent".equalsIgnoreCase(direction)) {
                        yield getPendingSentRelationshipsUseCase.execute(user.getId());
                    } else if ("received".equalsIgnoreCase(direction)) {
                        yield getPendingReceivedRelationshipsUseCase.execute(user.getId());
                    } else {
                        throw new IllegalArgumentException(
                                "When status is 'pending', direction must be 'sent' or 'received'");
                    }
                }
                default -> throw new IllegalArgumentException("Status must be 'accepted' or 'pending'");
            };

            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Relationships received successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(relationships);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);

        } catch (Exception e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
