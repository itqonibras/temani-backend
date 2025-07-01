package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

@RestController
public class BaseController {

	@GetMapping
	public ResponseEntity<?> home() {
		return ResponseEntity.ok(BaseResponse.success("Temani Backend Service"));
	}

	@GetMapping("/token-test")
	public ResponseEntity<?> tokenTest(Authentication auth) {
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		return ResponseEntity.ok(BaseResponse.success("Token valid!", user));
	}

	@GetMapping("/auth-debug")
	public ResponseEntity<?> authDebug(Authentication auth) {
		if (auth == null) {
			return ResponseEntity.ok(BaseResponse.error("No authentication found"));
		}
		
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		User user = userDetails.getUser();
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		
		List<String> authorityNames = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());
		
		return ResponseEntity.ok(BaseResponse.success("Auth Debug", 
			Map.of(
				"username", user.getUsername(),
				"userId", user.getId(),
				"userRoles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()),
				"authorities", authorityNames,
				"authenticated", auth.isAuthenticated(),
				"hasClientRole", authorityNames.contains("ROLE_CLIENT"),
				"hasCaregiverRole", authorityNames.contains("ROLE_CAREGIVER")
			)
		));
	}

}
