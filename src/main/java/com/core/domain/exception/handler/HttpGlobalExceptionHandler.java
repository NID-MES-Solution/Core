package com.core.domain.exception.handler;

import com.core.domain.dto.ResponseDTO;
import com.core.domain.exception.error.ErrorCode;
import com.core.domain.exception.type.CommonException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class HttpGlobalExceptionHandler {

    // Convertor 에서 바인딩 실패시 발생하는 예외
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseDTO<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("ExceptionHandler catch HttpMessageNotReadableException : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.BAD_REQUEST_JSON));
    }

    // 지원되지 않는 미디어 타입을 사용할 때 발생하는 예외
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseDTO<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("ExceptionHandler catch HttpMediaTypeNotSupportedException : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseDTO<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("ExceptionHandler catch NoHandlerFoundException : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.METHOD_NOT_ALLOWED));
    }

    // 지원되지 않는 HTTP 메소드를 사용할 때 발생하는 예외
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseDTO<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("ExceptionHandler catch HttpRequestMethodNotSupportedException : {}", e.getMessage());
        return ResponseDTO.fail(new CommonException(ErrorCode.METHOD_NOT_ALLOWED));
    }

    // Body Validation 에서 검증 실패시 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("ExceptionHandler catch MethodArgumentNotValidException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // Annotation Validation 에서 검증 실패시 발생하는 예외
    @ExceptionHandler(value = {HandlerMethodValidationException.class})
    public ResponseDTO<?> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("ExceptionHandler catch HandlerMethodValidationException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // Constraint Validation 에서 검증 실패시 발생하는 예외
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseDTO<?> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("ExceptionHandler catch ConstraintViolationException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {UnexpectedTypeException.class})
    public ResponseDTO<?> handleUnexpectedTypeException(UnexpectedTypeException e) {
        log.error("ExceptionHandler catch UnexpectedTypeException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 메소드의 인자 타입이 일치하지 않을 때 발생하는 예외
    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        log.error("ExceptionHandler catch MethodArgumentTypeMismatchException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 필수 파라미터가 누락되었을 때 발생하는 예외
    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseDTO<?> handleArgumentNotValidException(MissingServletRequestParameterException e) {
        log.error("ExceptionHandler catch MissingServletRequestParameterException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 개발자가 직접 정의한 예외
    @ExceptionHandler(value = {CommonException.class})
    public ResponseDTO<?> handleApiException(CommonException e) {
        log.error("ExceptionHandler catch HttpCommonException : {}", e.getMessage());
        return ResponseDTO.fail(e);
    }

    // 서버, DB 예외
    @ExceptionHandler(value = {Exception.class})
    public ResponseDTO<?> handleException(Exception e) {
        log.error("ExceptionHandler catch Exception : {}", e.getMessage());
        e.printStackTrace();
        return ResponseDTO.fail(new CommonException(ErrorCode.INTERNAL_SERVER_ERROR));
    }
}