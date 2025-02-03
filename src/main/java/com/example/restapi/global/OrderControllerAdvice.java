package com.example.restapi.global;

import com.example.restapi.Order.controller.OrderController;
import com.example.restapi.global.exception.OrderTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {OrderController.class})
public class OrderControllerAdvice {

    @ExceptionHandler(OrderTaskException.class)
    public ResponseEntity<ApiResponse<Void>> handleOrderTaskException(OrderTaskException ex) {
        return ResponseEntity.status(ex.getStatus())
                             .body(ApiResponse.failure(ex.getMessage(), ex.getStatus()));
    }
}
