package com.temani.temani.features.profile.presentation.dto.response;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.temani.temani.features.profile.domain.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Date createdAt;
    private Date updatedAt;
    private boolean isVerified;
    private Set<Role> roles;
}
