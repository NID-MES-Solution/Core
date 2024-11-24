package com.core.domain.dto;

import com.core.domain.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public sealed class ExceptionDTO permits ArgumentNotValidExceptionDTO {

    @JsonProperty("code")
    private final Integer code;

    @JsonProperty("message")
    private final String message;

    public ExceptionDTO(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public ExceptionDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ExceptionDTO of(ErrorCode errorCode) {
        return new ExceptionDTO(errorCode.getCode(), errorCode.getMessage());
    }

    public static ExceptionDTO of(ErrorCode errorCode, String message) {
        return new ExceptionDTO(errorCode.getCode(), message);
    }
}