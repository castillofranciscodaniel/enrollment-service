package com.org.enrollment.service.core.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class FindingExternalValueException extends CoreException {

    public FindingExternalValueException() {
    }

    public FindingExternalValueException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public FindingExternalValueException(HttpStatusCode code, String message) {
        super(code, message);
    }
}
