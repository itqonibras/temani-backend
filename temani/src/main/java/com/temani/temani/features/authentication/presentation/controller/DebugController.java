package com.temani.temani.features.authentication.presentation.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temani.temani.common.presentation.dto.response.BaseResponse;
import com.temani.temani.common.security.CustomUserDetails;

@RestController
@RequestMapping("/debug")
public class DebugController {

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(Authentication auth) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

            return ResponseEntity.ok(BaseResponse.success("User info retrieved", new UserInfoResponse(
                    userDetails.getUser().getId().toString(),
                    userDetails.getUser().getUsername(),
                    userDetails.getUser().getEmail(),
                    authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()),
                    userDetails.getUser().getRoles().stream().map(role -> role.getName())
                            .collect(Collectors.toList()))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(BaseResponse.error("Error: " + e.getMessage()));
        }
    }

    public static class UserInfoResponse {
        public String userId;
        public String username;
        public String email;
        public java.util.List<String> authorities;
        public java.util.List<String> roles;

        public UserInfoResponse(String userId, String username, String email,
                java.util.List<String> authorities, java.util.List<String> roles) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.authorities = authorities;
            this.roles = roles;
        }
    }
}

