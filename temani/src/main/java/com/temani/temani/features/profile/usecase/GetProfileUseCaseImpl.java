package com.temani.temani.features.profile.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temani.temani.common.constants.CommonMessages;
import com.temani.temani.features.profile.domain.repository.UserRepository;
import com.temani.temani.features.profile.infrastructure.mapper.UserDtoMapper;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetProfileUseCaseImpl implements GetProfileUseCase {

	private final UserRepository userRepository;
	private final UserDtoMapper userDtoMapper;

	@Override
	public UserResponse execute(UUID userId) {
		return userRepository.findById(userId)
				.map(userDtoMapper::toDto)
				.orElseThrow(() -> new IllegalArgumentException(CommonMessages.USER_NOT_FOUND));
	}

}

