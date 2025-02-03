package com.example.restapi.global;

import com.example.restapi.global.exception.ProductTaskException;
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
