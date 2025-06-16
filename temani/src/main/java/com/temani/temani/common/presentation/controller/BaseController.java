package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.JwtUtils;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
public class BaseController {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<?> home() {
        var baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Temani Backend Service");
        baseResponse.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/get-token")
    public ResponseEntity<?> getToken() {
        var baseResponse = new BaseResponse<>();

        Optional<User> optionalUser = userRepository.findByUsername("dummy_penyandang");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = jwtUtils.generateJwtToken(
                    user.getUsername(),
                    user.getId().toString(),
                    user.getRoles());
            baseResponse.setData(token);
        }

        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Token created successfully!");
        baseResponse.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/token-test")
    public ResponseEntity<?> tokenTest() {
        var baseResponse = new BaseResponse<>();
        baseResponse.setStatus(HttpStatus.OK.value());
        baseResponse.setMessage("Token valid!");
        baseResponse.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
