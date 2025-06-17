package com.temani.temani.features.profile.presentation.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.temani.temani.features.profile.domain.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String name;
    private String username;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verified;
    private Set<Role> roles;
    private ClientProfileResponse clientProfile;
    private CaregiverProfileResponse caregiverProfile;
    private PeerProfileResponse peerProfile;
}
