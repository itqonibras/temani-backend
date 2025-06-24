package com.temani.temani.features.authentication.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.constants.AuthMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.authentication.usecase.LoginUseCase;
import com.temani.temani.features.authentication.usecase.RegisterUseCase;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final LoginUseCase loginUseCase;

	private final RegisterUseCase registerUseCase;

	@PostMapping("/login")
	public ResponseEntity<?> userLogin(@RequestBody @Valid LoginRequest request) {
		try {
			String token = loginUseCase.execute(request);
			return ResponseEntity.ok(BaseResponse.success(AuthMessages.LOGIN_SUCCESS, token));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> userRegister(@RequestBody @Valid RegisterRequest request) {
		try {
			UserResponse registeredUser = registerUseCase.execute(request);
			return ResponseEntity.ok(BaseResponse.success(AuthMessages.REGISTER_SUCCESS, registeredUser));
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
		}
	}

}
