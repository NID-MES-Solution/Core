package com.core.domain.dto;

import com.core.domain.exception.error.ErrorCode;
import com.core.domain.exception.type.CommonException;
import com.core.domain.exception.type.HttpSecurityException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Nullable;

@Getter
public class ResponseDTO<T> extends SelfValidating<ResponseDTO<T>> {

    @JsonIgnore
    private final HttpStatus httpStatus;

    @NotNull
    private final Boolean success;

    @Nullable
    private final T data;

    @Nullable
    private final ExceptionDTO error;

    @Builder
    private ResponseDTO(
            HttpStatus httpStatus,
            Boolean success,
            @Nullable T data,
            @Nullable ExceptionDTO error
    ) {
        this.httpStatus = httpStatus;
        this.success = success;
        this.data = data;
        this.error = error;

        this.validateSelf();
    }

    public static <T> ResponseDTO<T> ok(@Nullable final T data) {
        return ResponseDTO.<T>builder()
                .httpStatus(HttpStatus.OK)
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static <T> ResponseDTO<T> created(@Nullable final T data) {
        return ResponseDTO.<T>builder()
                .httpStatus(HttpStatus.CREATED)
                .success(true)
                .data(data)
                .error(null)
                .build();
    }

    public static ResponseDTO<Object> noContent() {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.NO_CONTENT)
                .success(true)
                .data(null)
                .error(null)
                .build();
    }

    public static ResponseDTO<Object> fail(final ConstraintViolationException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDTO(e))
                .build();
    }

    public static ResponseDTO<?> fail(HandlerMethodValidationException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDTO(e))
                .build();
    }

    public static ResponseDTO<Object> fail(final UnexpectedTypeException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .success(false)
                .data(null)
                .error(ExceptionDTO.of(ErrorCode.INVALID_PARAMETER_FORMAT))
                .build();
    }

    public static <T> ResponseDTO<T> fail(final MethodArgumentNotValidException e) {
        return ResponseDTO.<T>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(new ArgumentNotValidExceptionDTO(e))
                .build();
    }

    public static ResponseDTO<Object> fail(final MissingServletRequestParameterException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(ExceptionDTO.of(ErrorCode.MISSING_REQUEST_PARAMETER))
                .build();
    }

    public static ResponseDTO<Object> fail(final MethodArgumentTypeMismatchException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .success(false)
                .data(null)
                .error(ExceptionDTO.of(ErrorCode.INVALID_PARAMETER_FORMAT))
                .build();
    }

    public static ResponseDTO<Object> fail(final HttpSecurityException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .success(false)
                .data(null)
                .error(ExceptionDTO.of(e.getErrorCode()))
                .build();
    }

    public static ResponseDTO<Object> fail(final CommonException e) {
        return ResponseDTO.<Object>builder()
                .httpStatus(e.getErrorCode().getHttpStatus())
                .success(false)
                .data(null)
                .error(ExceptionDTO.of(e.getErrorCode()))
                .build();
    }
}
