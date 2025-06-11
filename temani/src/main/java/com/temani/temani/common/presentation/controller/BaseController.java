package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponseDTO;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class BaseController {
    @GetMapping("/")
    public ResponseEntity<?> home() {
        var baseResponseDTO = new BaseResponseDTO<>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setMessage("Temani Backend Service");
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }   
}
