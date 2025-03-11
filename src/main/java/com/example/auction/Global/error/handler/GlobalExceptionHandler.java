package com.example.auction.Global.error.handler;

import com.example.auction.Global.CommonResponseBody;
import com.example.auction.Global.error.exception.CustomException;
import com.example.auction.Global.error.response.ErrorResponse;
import com.example.auction.Global.error.response.ErrorResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import static com.example.auction.Global.error.errorcode.ErrorCode.DUPLICATE_RESOURCE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //hibernate 관련 에러를 처리
    @ExceptionHandler(value = { ConstraintViolationException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponse> handleDataException() {
        log.error("handleDataException throw Exception : {}", DUPLICATE_RESOURCE);
        return ErrorResponse.toResponseEntity(DUPLICATE_RESOURCE);
    }

    /**
     *
     * @param e CustomException
     * @return ErrorResponse
     */
    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }


    @Override
    @Hidden
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.", LocalDateTime.now());
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorResponseDto.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(errorResponseDto);
    }

    // Security와 관련된 AuthenticationException 예외 처리.
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<CommonResponseBody<Void>> handleAuthException(
            AuthenticationException e) {
        HttpStatus statusCode = e instanceof BadCredentialsException
                ? HttpStatus.FORBIDDEN
                : HttpStatus.UNAUTHORIZED;

        return ResponseEntity
                .status(statusCode)
                .body(new CommonResponseBody<>(e.getMessage()));
    }

    // Security와 관련된 AccessDeniedException 예외 처리.
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<CommonResponseBody<Void>> handleAccessDeniedException(
            AccessDeniedException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CommonResponseBody<>(e.getMessage()));
    }

    // Security와 관련된 AuthorizationDeniedException 예외 처리.
    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<CommonResponseBody<Void>> handleAuthorizationDeniedException(
            AuthorizationDeniedException e) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CommonResponseBody<>(e.getMessage()));
    }

    // JWT와 관련된 JwtException 예외 처리.
    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<CommonResponseBody<Void>> handleJwtException(JwtException e) {
        HttpStatus httpStatus = e instanceof ExpiredJwtException
                ? HttpStatus.UNAUTHORIZED
                : HttpStatus.FORBIDDEN;

        return ResponseEntity
                .status(httpStatus)
                .body(new CommonResponseBody<>(e.getMessage()));
    }
}