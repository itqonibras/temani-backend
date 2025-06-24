package com.temani.temani.features.relationship.presentation.dto.response;

import java.util.Set;
import java.util.UUID;

import com.temani.temani.features.profile.domain.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PotentialRelationshipResponse {
    private UUID userId;
    private String name;
    private String username;
    private Set<Role> roles;
    private String relationshipStatus;
}
