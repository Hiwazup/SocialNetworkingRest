package com.martin.exceptions;

import org.springframework.http.HttpStatus;

public class RestApiException extends Exception {
    private HttpStatus statusCode;
    private String message;

    public RestApiException(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }
}
