package com.temanmu.temanmu.features.authentication.usecase;

import com.temanmu.temanmu.features.authentication.presentation.dto.request.LoginRequest;
import com.temanmu.temanmu.features.authentication.presentation.dto.response.LoginResponse;

public interface LoginUseCase {

	LoginResponse execute(LoginRequest request);

}
