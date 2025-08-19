package com.temani.temani.features.authentication.usecase;

import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.authentication.presentation.dto.response.LoginResponse;

public interface LoginUseCase {

	LoginResponse execute(LoginRequest request);

}
