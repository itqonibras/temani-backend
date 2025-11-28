package com.temanmu.temanmu.features.authentication.usecase;

import com.temanmu.temanmu.features.authentication.presentation.dto.request.RegisterRequest;
import com.temanmu.temanmu.features.profile.presentation.dto.response.UserResponse;

public interface RegisterUseCase {

	UserResponse execute(RegisterRequest request);

}
