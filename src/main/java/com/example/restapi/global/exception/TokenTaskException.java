package com.example.restapi.global.exception;

import org.springframework.http.HttpStatus;

public class TokenTaskException extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public TokenTaskException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }


    public enum Exceptions {
        NO_ACCESS_TOKEN("No Access Token", HttpStatus.BAD_REQUEST),
        NO_REFRESH_TOKEN("No Refresh Token", HttpStatus.BAD_REQUEST),
        NO_MID("No mid", HttpStatus.BAD_REQUEST),
        INVALID_HOST("Invalid Host", HttpStatus.BAD_REQUEST),
        TOKEN_EXPIRED("Token has expired", HttpStatus.UNAUTHORIZED),
        TOKEN_EXPIRED_LOGIN_REQUIRED("Access and refresh tokens are expired. Please login again.", HttpStatus.UNAUTHORIZED);

        private final String message;
        private final HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public TokenTaskException get() {
            return new TokenTaskException(message, status);
        }
    }
}
