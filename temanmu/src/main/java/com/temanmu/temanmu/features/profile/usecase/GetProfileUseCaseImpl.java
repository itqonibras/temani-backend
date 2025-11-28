package com.temanmu.temanmu.features.profile.usecase;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.temanmu.temanmu.common.constants.CommonMessages;
import com.temanmu.temanmu.features.profile.domain.repository.UserRepository;
import com.temanmu.temanmu.features.profile.infrastructure.mapper.UserDtoMapper;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

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

