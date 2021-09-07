package com.api.sendaparcel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAllowedResourceException extends RuntimeException {

    public NotAllowedResourceException() {
        super();
    }

    public NotAllowedResourceException(String message) {
        super(message);
    }

    public NotAllowedResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
