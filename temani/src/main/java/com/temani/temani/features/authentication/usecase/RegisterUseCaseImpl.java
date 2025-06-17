package com.temani.temani.features.authentication.usecase;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.profile.domain.model.CaregiverProfile;
import com.temani.temani.features.profile.domain.model.ClientProfile;
import com.temani.temani.features.profile.domain.model.PeerProfile;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.RoleRepository;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.profile.infrastructure.mapper.UserDtoMapper;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final HashPasswordUseCase hashPasswordUseCase;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDtoMapper userMapper;

    @Transactional
    @Override
    public UserResponse execute(RegisterRequest request) {
        LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth());

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        ClientProfile clientProfile = containsRole(roles, "CLIENT")
            ? new ClientProfile(null, null , null) : null;
        CaregiverProfile caregiverProfile = containsRole(roles, "CAREGIVER")
            ? new CaregiverProfile(null) : null;
        PeerProfile peerProfile = containsRole(roles, "PEER")
            ? new PeerProfile(null) : null;

        User user = new User(
                null,
                request.getName(),
                request.getUsername(),
                dateOfBirth,
                request.getEmail(),
                request.getPhone(),
                hashPasswordUseCase.hash(request.getPassword()),
                null,
                null,
                false,
                roles,
                clientProfile,
                caregiverProfile,
                peerProfile);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    private boolean containsRole(Set<Role> roles, String roleName) {
        return roles.stream().anyMatch(role -> role.getName().equalsIgnoreCase(roleName));
    }
}
