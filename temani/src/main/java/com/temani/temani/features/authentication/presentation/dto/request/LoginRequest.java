package com.temani.temani.features.authentication.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

	@NotBlank(message = "Email or Username can't be empty!")
	private String emailOrUsername;

	@NotBlank(message = "Password can't be empty!")
	private String password;

}
