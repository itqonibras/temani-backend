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

@RestController
@RequiredArgsConstructor
@RequestMapping("/journals")
public class JournalController {

	private final GetAllJournalsUseCase getAllJournalsUseCase;

	private final CreateJournalUseCase createJournalUseCase;

	private final UpdateJournalUseCase updateJournalUseCase;

	private final DeleteJournalUseCase deleteJournalUseCase;

	@GetMapping
	public ResponseEntity<?> getJournals(Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			List<JournalResponse> journals = getAllJournalsUseCase.execute(user.getId());
			return ResponseEntity.ok(BaseResponse.success("Journals received successfully!", journals));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PostMapping
	public ResponseEntity<?> createJournal(@RequestBody @Valid JournalRequest request, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			JournalResponse journal = createJournalUseCase.execute(request, user.getId());
			return ResponseEntity.ok(BaseResponse.success("Journals created successfully!", journal));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateJournal(@PathVariable UUID id, @RequestBody @Valid JournalRequest request,
			Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			JournalResponse journal = updateJournalUseCase.execute(request, id, user.getId());
			return ResponseEntity.ok(BaseResponse.success("Journals created successfully!", journal));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteJournal(@PathVariable UUID id, Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		try {
			deleteJournalUseCase.execute(id, user.getId());
			return ResponseEntity
				.ok(BaseResponse.success(String.format("Journal with id %s deleted successfully!", id)));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}
