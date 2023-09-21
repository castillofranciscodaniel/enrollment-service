package com.org.enrollment.service.core.exeption;

import org.springframework.http.HttpStatusCode;

public class RequestLogSaveException extends CoreException {

    public RequestLogSaveException() {
    }

    public RequestLogSaveException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
