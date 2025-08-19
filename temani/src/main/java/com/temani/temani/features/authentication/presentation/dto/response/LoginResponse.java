package com.temani.temani.features.authentication.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private String userId;
	private String username;
}
