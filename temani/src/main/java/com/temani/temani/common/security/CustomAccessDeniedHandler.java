package com.temani.temani.common.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temani.temani.common.presentation.dto.response.BaseResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        BaseResponse<Object> responseDTO = new BaseResponse<>();
        responseDTO.setStatus(HttpServletResponse.SC_FORBIDDEN);
        responseDTO.setMessage("You don't have access to this endpoint!");
        responseDTO.setTimestamp(new Date());

        String jsonResponse = objectMapper.writeValueAsString(responseDTO);
        response.getWriter().write(jsonResponse);
    }
}
