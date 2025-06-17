package com.temani.temani.features.authentication.usecase;

import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;

public interface LoginUseCase {
    String execute(LoginRequest request);
}
