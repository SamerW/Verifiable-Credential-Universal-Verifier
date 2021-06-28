package com.crosswordcybersecurity.exceptions;

public class BadVpJwtException extends Exception {
    public BadVpJwtException() {
    }

    public BadVpJwtException(String message) {
        super(message);
    }

    public BadVpJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadVpJwtException(Throwable cause) {
        super(cause);
    }

    public BadVpJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
