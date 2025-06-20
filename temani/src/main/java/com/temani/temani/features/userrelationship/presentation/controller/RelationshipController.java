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
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.userrelationship.presentation.dto.request.RelationshipRequest;
import com.temani.temani.features.userrelationship.presentation.dto.response.RelationshipResponse;
import com.temani.temani.features.userrelationship.usecase.CreateRelationshipUseCase;
import com.temani.temani.features.userrelationship.usecase.GetAcceptedRelationshipsUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/relationships")
public class RelationshipController {

    private final CreateRelationshipUseCase createRelationshipUseCase;
    private final GetAcceptedRelationshipsUseCase getAcceptedRelationshipsUseCase;

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

    @GetMapping("/accepted")
    public ResponseEntity<?> getAcceptedRelationships(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            List<RelationshipResponse> relationships = getAcceptedRelationshipsUseCase.execute(user.getId());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Relationships received successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(relationships);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Error e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
