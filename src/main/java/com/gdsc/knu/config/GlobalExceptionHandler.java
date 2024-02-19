package com.gdsc.knu.config;

import com.gdsc.knu.exception.ForbiddenException;
import com.gdsc.knu.exception.NotFoundException;
import com.gdsc.knu.exception.UnauthorizedException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(NotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        log.info("[UserNotFoundException] : " + ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, ex.getMessage());
        log.info("[UnauthorizedException] : " + ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException ex) {
        ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, ex.getMessage());
        log.info("[ForbiddenException] : " + ex.getMessage());
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Getter
    private static class ApiError {
        private HttpStatus status;
        private String message;

        public ApiError(HttpStatus status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}
