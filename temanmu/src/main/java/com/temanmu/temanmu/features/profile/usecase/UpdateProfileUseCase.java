package com.temanmu.temanmu.features.profile.usecase;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.temanmu.temanmu.features.profile.presentation.dto.request.UpdateProfileRequest;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

public interface UpdateProfileUseCase {

	UserResponse execute(UUID userId, UpdateProfileRequest request, MultipartFile profilePicture);

}

