package com.temanmu.temanmu.features.home.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temanmu.temanmu.common.constants.HomeMessages;
import com.temanmu.temanmu.common.presentation.dto.response.BaseResponse;
import com.temanmu.temanmu.common.security.CustomUserDetails;
import com.temanmu.temanmu.features.home.usecase.GetHomeDataUseCase;
import com.temanmu.temanmu.features.profile.domain.model.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private final GetHomeDataUseCase getHomeDataUseCase;

    @GetMapping
    public ResponseEntity<?> getHome(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();
        try {
            var data = getHomeDataUseCase.execute(user.getId());
            return ResponseEntity.ok(BaseResponse.success(HomeMessages.HOME_DATA_RECEIVED_SUCCESS, data));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error(e.getMessage()));
        }
    }
}
