package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;
import com.temani.temani.features.profile.domain.model.User;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BaseController {

    @GetMapping("/")
    public ResponseEntity<?> home() {
        var baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Temani Backend Service");
        baseResponse.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/token-test")
    public ResponseEntity<?> tokenTest(Authentication auth) {
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        User user = userDetails.getUser();

        var baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Token valid!");
        baseResponse.setTimestamp(LocalDateTime.now());
        baseResponse.setData(user);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
