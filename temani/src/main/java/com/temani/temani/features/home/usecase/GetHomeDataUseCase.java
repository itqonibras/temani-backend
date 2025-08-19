package com.temani.temani.features.home.usecase;

import java.util.UUID;

import com.temani.temani.features.home.presentation.dto.HomeResponse;

public interface GetHomeDataUseCase {

    HomeResponse execute(UUID userId);

}
