package com.digicade.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @Autowired
    private ExceptionMessage exceptionMessage;

    @ExceptionHandler(UserNotFoundCustomException.class)
    public ResponseEntity<ExceptionMessage> customUserNotFoundException(UserNotFoundCustomException ex) {
        exceptionMessage.setMessage(ex.getMessage());
        exceptionMessage.setStatus(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }
}
