package com.api.sendaparcel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequestDataValidationException extends RuntimeException {

    public RequestDataValidationException() {
        super();
    }

    public RequestDataValidationException(String message) {
        super(message);
    }

    public RequestDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
