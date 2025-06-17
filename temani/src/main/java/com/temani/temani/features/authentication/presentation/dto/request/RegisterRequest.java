package com.temani.temani.features.authentication.presentation.dto.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String name;
    private String username;
    private String dateOfBirth;
    private String email;
    private String phone;
    private String password;
    private Set<String> roles;
}
