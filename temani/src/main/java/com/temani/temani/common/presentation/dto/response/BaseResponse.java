package com.temani.temani.common.presentation.dto.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BaseResponse<T> {

	private int status;

	private String message;

	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime timestamp;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public static <T> BaseResponse<T> success(String message, T data) {
		BaseResponse<T> res = new BaseResponse<>();
		res.setStatus(HttpStatus.OK.value());
		res.setMessage(message);
		res.setTimestamp(LocalDateTime.now());
		res.setData(data);
		return res;
	}

	public static <T> BaseResponse<T> success(String message) {
		return success(message, null);
	}

	public static <T> BaseResponse<T> error(String message) {
		BaseResponse<T> res = new BaseResponse<>();
		res.setStatus(HttpStatus.BAD_REQUEST.value());
		res.setMessage(message);
		res.setTimestamp(LocalDateTime.now());
		return res;
	}

}
