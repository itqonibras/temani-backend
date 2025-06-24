package com.temani.temani.features.authentication.usecase;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordEncoderUseCaseImpl implements PasswordEncoderUseCase {

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public String hash(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public boolean matches(String rawPassword, String hashedPassword) {
		return passwordEncoder.matches(rawPassword, hashedPassword);
	}

}
