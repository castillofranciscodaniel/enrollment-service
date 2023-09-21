package com.org.enrollment.service.core.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class RateLimitException extends CoreException {

    public RateLimitException() {
        super(HttpStatus.TOO_MANY_REQUESTS, "you just can do three request per minutes");
    }

    public RateLimitException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public RateLimitException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
