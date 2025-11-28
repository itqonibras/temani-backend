package com.temanmu.temanmu.features.home.usecase;

import java.util.UUID;

import com.temanmu.temanmu.features.home.presentation.dto.HomeResponse;

public interface GetHomeDataUseCase {

    HomeResponse execute(UUID userId);

}
