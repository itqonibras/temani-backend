package com.temanmu.temanmu.features.profile.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

public interface GetProfileUseCase {

	UserResponse execute(UUID userId);

}

