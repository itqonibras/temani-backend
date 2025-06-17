package com.temani.temani.features.authentication.usecase;

import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

public interface RegisterUseCase {
    UserResponse execute(RegisterRequest request);
}
