package com.org.enrollment.service.infraestructure.config;

import com.org.enrollment.service.core.exeption.CoreException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {

    @ExceptionHandler(CoreException.class)
    public ResponseEntity<ErrorResponseDto> handleCoreException(CoreException e) {
        return ResponseEntity.status(e.getCode())
                .body(this.buildErrorResponseDto(e));
    }

    private ErrorResponseDto buildErrorResponseDto(CoreException e) {
        return  new ErrorResponseDto(e.getCode(), e.getMessage());
    }

}