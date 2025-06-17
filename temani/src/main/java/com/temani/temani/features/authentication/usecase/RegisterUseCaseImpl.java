package com.temani.temani.features.authentication.usecase;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.RoleRepository;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.profile.infrastructure.mapper.UserDtoMapper;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterUseCaseImpl implements RegisterUseCase {

    private final HashPasswordUseCase hashPasswordUseCase;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserDtoMapper userMapper;

    @Override
    public UserResponse execute(RegisterRequest request) {
        LocalDate dateOfBirth = LocalDate.parse(request.getDateOfBirth());

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

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
                roles);

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

}
