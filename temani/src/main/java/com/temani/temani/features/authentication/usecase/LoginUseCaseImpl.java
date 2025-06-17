package com.temani.temani.features.authentication.usecase;

import org.springframework.stereotype.Service;

import com.temani.temani.common.security.JwtUtils;
import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final HashPasswordUseCase hashPasswordUseCase;
    private final JwtUtils jwtUtils;

    @Override
    public String execute(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!hashPasswordUseCase.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        String token = jwtUtils.generateJwtToken(user.getUsername(), user.getId().toString(), user.getRoles());

        return token;
    }

}
