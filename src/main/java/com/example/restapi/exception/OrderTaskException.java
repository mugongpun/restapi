package com.example.restapi.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class OrderTaskException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public OrderTaskException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.message = message;
        this.status = status;
    }

    public OrderTaskException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public enum Exceptions {
        NOT_ENOUGH_STOCK_EXCEPTION("NEED MORE STOCK", HttpStatus.CONFLICT),
        NEGATIVE_PRICE_EXCEPTION("Product price must be greater than or equal to zero", HttpStatus.BAD_REQUEST);

        private String message;
        private HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public OrderTaskException value() {
            return new OrderTaskException(message, status);
        }
    }


}
