package com.digicade.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ExceptionMessage {

    private String message;
    private HttpStatus status;

    public ExceptionMessage() {}

    public ExceptionMessage(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
