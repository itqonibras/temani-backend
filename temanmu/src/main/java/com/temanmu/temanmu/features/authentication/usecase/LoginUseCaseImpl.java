package com.temanmu.temanmu.features.authentication.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.constants.AuthMessages;
import com.temanmu.temanmu.common.constants.CommonMessages;
import com.temanmu.temanmu.common.security.JwtUtils;
import com.temanmu.temanmu.features.authentication.presentation.dto.request.LoginRequest;
import com.temanmu.temanmu.features.authentication.presentation.dto.response.LoginResponse;
import com.temanmu.temanmu.features.profile.domain.model.Role;
import com.temanmu.temanmu.features.profile.domain.model.User;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUseCaseImpl implements LoginUseCase {

	private final UserRepository userRepository;

	private final PasswordEncoderUseCase passwordEncoderUseCase;

	private final JwtUtils jwtUtils;

	@Override
	public LoginResponse execute(LoginRequest request) {
		User user = userRepository.findByEmailOrUsername(request.getEmailOrUsername())
				.orElseThrow(() -> new IllegalArgumentException(CommonMessages.USER_NOT_FOUND));

		if (!passwordEncoderUseCase.matches(request.getPassword(), user.getPassword())) {
			throw new IllegalArgumentException(AuthMessages.INCORRECT_PASSWORD);
		}

		String token = jwtUtils.generateJwtToken(user.getUsername(), user.getId().toString(), user.getRoles());

		// Extract role names from user roles
		List<String> roleNames = user.getRoles().stream()
				.map(Role::getName)
				.collect(Collectors.toList());

		return new LoginResponse(token, user.getId().toString(), user.getUsername(), roleNames);
	}

}
