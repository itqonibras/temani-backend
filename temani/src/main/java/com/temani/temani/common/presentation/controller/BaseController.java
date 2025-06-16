package com.temani.temani.common.presentation.controller;

import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponseDTO;
import com.temani.temani.common.security.JwtUtils;
import com.temani.temani.features.profile.domain.model.Role;
import com.temani.temani.features.profile.domain.model.User;
import com.temani.temani.features.profile.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        var baseResponseDTO = new BaseResponseDTO<>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setMessage("Temani Backend Service");
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-token")
    public ResponseEntity<?> getToken() {
        var baseResponseDTO = new BaseResponseDTO<>();

        Optional<User> optionalUser = userRepository.findByUsername("dummy_penyandang");
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Set<Role> roles = user.getRoles();
            Set<String> rolesNames = roles.stream().map(Role::getName).collect(Collectors.toSet());
            String token = jwtUtils.generateJwtToken(
                    user.getUsername(),
                    user.getId().toString(),
                    rolesNames);
            baseResponseDTO.setData(token);
        }

        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setMessage("Token created successfully!");
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/token-test")
    public ResponseEntity<?> tokenTest() {
        var baseResponseDTO = new BaseResponseDTO<>();
        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setMessage("Token valid!");
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }
}
