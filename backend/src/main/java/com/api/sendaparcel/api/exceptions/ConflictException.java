package com.api.sendaparcel.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException() {
        super("Something went wrong!");
    }

    public ConflictException(String message) {
        super(message);
    }
}
