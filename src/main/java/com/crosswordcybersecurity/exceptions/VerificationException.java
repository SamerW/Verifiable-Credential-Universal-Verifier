package com.crosswordcybersecurity.exceptions;

import org.springframework.http.HttpStatus;

public class VerificationException extends Exception{

    private String reason;
    private HttpStatus errorCode;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public HttpStatus getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }
}
