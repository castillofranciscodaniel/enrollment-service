package com.org.enrollment.service.infraestructure.config;

import org.springframework.http.HttpStatusCode;

public class ErrorResponseDto {

    private final HttpStatusCode statusCode;
    private final String message;

    private final Integer code;

    public ErrorResponseDto(HttpStatusCode statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.code = statusCode.value();
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + statusCode +
                ", message='" + message + '\'' +
                '}';
    }
}