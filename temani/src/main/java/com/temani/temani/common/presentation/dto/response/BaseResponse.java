package com.temani.temani.common.presentation.dto.response;

import java.util.Date;

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
    private Date timestamp;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
