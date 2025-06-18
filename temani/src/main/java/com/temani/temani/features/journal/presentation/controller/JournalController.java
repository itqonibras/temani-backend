package com.temani.temani.features.journal.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.journal.presentation.dto.request.JournalRequest;
import com.temani.temani.features.journal.presentation.dto.response.JournalResponse;
import com.temani.temani.features.journal.usecase.CreateJournalUseCase;
import com.temani.temani.features.journal.usecase.DeleteJournalUseCase;
import com.temani.temani.features.journal.usecase.GetAllJournalsUseCase;
import com.temani.temani.features.journal.usecase.UpdateJournalUseCase;
import com.temani.temani.features.profile.domain.model.User;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/journals")
public class JournalController {

    private final GetAllJournalsUseCase getAllJournalsUseCase;
    private final CreateJournalUseCase createJournalUseCase;
    private final UpdateJournalUseCase updateJournalUseCase;
    private final DeleteJournalUseCase deleteJournalUseCase;

    @GetMapping("")
    public ResponseEntity<?> getJournals(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            List<JournalResponse> journals = getAllJournalsUseCase.execute(user.getId());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Journals received successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(journals);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (Error e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createJournal(@RequestBody @Valid JournalRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            JournalResponse journal = createJournalUseCase.execute(request, user.getId());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Journal created successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(journal);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournal(@PathVariable UUID id,
            @RequestBody @Valid JournalRequest request,
            Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            JournalResponse updatedJournal = updateJournalUseCase.execute(request, id, user.getId());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Journal updated successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(updatedJournal);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable UUID id, Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        try {
            deleteJournalUseCase.execute(id, user.getId());
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage(String.format("Journal with id %s deleted successfully!", id));
            baseResponse.setTimestamp(LocalDateTime.now());
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

}
