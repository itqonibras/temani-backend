package com.temani.temani.features.profile.presentation.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

	private String name;

	private String username;

	private LocalDate dateOfBirth;

	@Email(message = "Email must be valid")
	private String email;

	@Pattern(regexp = "^[0-9]+$", message = "Phone must contain only numbers")
	private String phone;

}

