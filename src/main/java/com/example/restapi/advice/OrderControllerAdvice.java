package com.example.restapi.advice;

import com.example.restapi.controller.ApiResponse;
import com.example.restapi.controller.OrderController;
import com.example.restapi.exception.OrderTaskException;
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
