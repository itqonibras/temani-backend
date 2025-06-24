package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BaseController {

	@GetMapping("/")
	public ResponseEntity<?> home() {
		return ResponseEntity.ok(BaseResponse.success("Temani Backend Service"));
	}

	@GetMapping("/token-test")
	public ResponseEntity<?> tokenTest(Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		return ResponseEntity.ok(BaseResponse.success("Token valid!", user));
	}

}
