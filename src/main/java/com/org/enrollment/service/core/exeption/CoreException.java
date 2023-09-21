package com.org.enrollment.service.core.exeption;

import org.springframework.http.HttpStatusCode;

public class CoreException extends RuntimeException {

    private HttpStatusCode code;

    private String message;

    public CoreException() {
        super();
        super.setStackTrace(new StackTraceElement[0]);
    }

    public CoreException(HttpStatusCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        super.setStackTrace(new StackTraceElement[0]);
    }

    public HttpStatusCode getCode() {
        return code;
    }

    public void setCode(HttpStatusCode code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CoreException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
