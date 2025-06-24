package com.temani.temani.features.authentication.usecase;

import org.springframework.stereotype.Service;

import com.temani.temani.common.constants.AuthMessages;
import com.temani.temani.common.constants.CommonMessages;
import com.temani.temani.common.security.JwtUtils;
import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderUseCase passwordEncoderUseCase;
    private final JwtUtils jwtUtils;

    @Override
    public String execute(LoginRequest request) {
        User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new IllegalArgumentException(CommonMessages.USER_NOT_FOUND));

        if (!passwordEncoderUseCase.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException(AuthMessages.INCORRECT_PASSWORD);
        }

        String token = jwtUtils.generateJwtToken(user.getUsername(), user.getId().toString(), user.getRoles());

        return token;
    }

}
