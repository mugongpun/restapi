package com.example.restapi.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
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
        NOT_ENOUGH_STOCK_EXCEPTION("재고가 부족합니다", HttpStatus.CONFLICT),
        NEGATIVE_PRICE_EXCEPTION("상품 가격은 0원보다 커야합니다", HttpStatus.BAD_REQUEST),
        NOT_PRODUCT_LIST("주문 상품 리스트가 존재하지 않습니다", HttpStatus.CONFLICT),
        NOT_FOUND_ORDER("존재하지 않는 주문입니다", HttpStatus.CONFLICT),
        NOT_FOUND_ORDER_LIST("해당 아이디로 주문하신 내역이 없습니다",HttpStatus.BAD_REQUEST);

        private String message;
        private HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public OrderTaskException get() {
            return new OrderTaskException(message, status);
        }
    }


}
