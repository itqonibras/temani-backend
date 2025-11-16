package com.temani.temani.features.profile.usecase;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.temani.temani.features.profile.presentation.dto.request.UpdateProfileRequest;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

public interface UpdateProfileUseCase {

	UserResponse execute(UUID userId, UpdateProfileRequest request, MultipartFile profilePicture);

}

