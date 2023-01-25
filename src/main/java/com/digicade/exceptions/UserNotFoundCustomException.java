package com.digicade.exceptions;

public class UserNotFoundCustomException extends Exception {

    public UserNotFoundCustomException() {
        super();
    }

    public UserNotFoundCustomException(String message) {
        super(message);
    }

    public UserNotFoundCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundCustomException(Throwable cause) {
        super(cause);
    }

    protected UserNotFoundCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
