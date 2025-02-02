package com.example.restapi.exception;

import org.springframework.http.HttpStatus;

public class ProductTaskException extends RuntimeException{
    private String message;
    private HttpStatus status;

    public ProductTaskException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    public ProductTaskException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public enum Exceptions {
        DUPLICATE_PRODUCT("이미 존재하는 상품입니다", HttpStatus.CONFLICT),
        NOT_EXIST_PRODUCT("존재하지 않는 상품입니다", HttpStatus.NOT_FOUND);
        private String message;
        private HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public ProductTaskException get() {
            return new ProductTaskException(message, status);
        }
    }
}
