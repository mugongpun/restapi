package com.example.restapi.advice;

import com.example.restapi.controller.ApiResponse;
import com.example.restapi.controller.ErrorDetail;
import com.example.restapi.exception.ProductTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(ProductTaskException.class)
    public ResponseEntity<ApiResponse<ErrorDetail>> handleProductException() {
        return null;
    }
}
