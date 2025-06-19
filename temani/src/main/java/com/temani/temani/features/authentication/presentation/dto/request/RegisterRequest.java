package com.temani.temani.features.authentication.presentation.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Name can't be empty!")
    private String name;

    @NotBlank(message = "Username can't be empty!")
    @Pattern(regexp = "^[a-z0-9._]+$", message = "Username can only contain lowercase letters, numbers, dot, and underscore, no spaces or uppercase letters allowed")
    private String username;

    @NotBlank(message = "Date of birth can't be empty!")
    private String dateOfBirth;

    @Email(message = "Email tidak valid")
    private String email;

    @NotBlank(message = "Phone number can't be empty!")
    private String phone;

    @NotBlank(message = "Password can't be empty!")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotEmpty(message = "Roles can't be empty!")
    private Set<String> roles;
}
