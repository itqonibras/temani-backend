package com.temani.temani.features.authentication.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.features.authentication.presentation.dto.request.LoginRequest;
import com.temani.temani.features.authentication.usecase.LoginUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginRequest request) {
        var baseResponse = new BaseResponse<>();
        try {
            String token = loginUseCase.execute(request);
            baseResponse.setStatus(HttpStatus.OK.value());
            baseResponse.setMessage("Login successful!");
            baseResponse.setTimestamp(new Date());
            baseResponse.setData(token);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            baseResponse.setStatus(HttpStatus.BAD_REQUEST.value());
            baseResponse.setMessage(e.getMessage());
            baseResponse.setTimestamp(new Date());
            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        }        
    }
    
}
