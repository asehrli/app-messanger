package org.example.appsimplemassenger.exception;

import org.springframework.http.HttpStatus;

public class MyNotFoundException extends MyException {
    public MyNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
