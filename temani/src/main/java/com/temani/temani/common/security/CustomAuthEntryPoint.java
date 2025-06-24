package com.temani.temani.common.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.temani.temani.common.constants.CommonMessages;
import com.temani.temani.common.presentation.dto.response.BaseResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	public CustomAuthEntryPoint(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		var baseResponse = new BaseResponse<>();
		baseResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
		baseResponse.setMessage(CommonMessages.UNAUTHORIZED);
		baseResponse.setTimestamp(LocalDateTime.now());

		String jsonString = objectMapper.writeValueAsString(baseResponse);
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(jsonString);
	}

}
