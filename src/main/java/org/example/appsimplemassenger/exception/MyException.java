package org.example.appsimplemassenger.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class MyException extends RuntimeException {
    private final HttpStatus status;
    protected MyException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
