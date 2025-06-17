package com.temani.temani.features.authentication.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.authentication.presentation.dto.request.RegisterRequest;
import com.temani.temani.features.authentication.usecase.LoginUseCase;
import com.temani.temani.features.authentication.usecase.RegisterUseCase;
import com.temani.temani.features.profile.presentation.dto.response.UserResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;
    
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginRequest request) {
        var baseResponse = new BaseResponse<>();
        try {
            String token = loginUseCase.execute(request);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Login successful!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(token);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }        
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody @Valid RegisterRequest request) {
        var baseResponse = new BaseResponse<>();
        try {
            UserResponse registeredUser = registerUseCase.execute(request);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Account registered successfully!");
            baseResponse.setTimestamp(LocalDateTime.now());
            baseResponse.setData(registeredUser);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(LocalDateTime.now());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
