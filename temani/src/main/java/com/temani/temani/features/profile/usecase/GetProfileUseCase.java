package com.temani.temani.features.profile.usecase;

import java.util.UUID;

import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

public interface GetProfileUseCase {

	UserResponse execute(UUID userId);

}

