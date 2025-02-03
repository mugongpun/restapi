package com.example.restapi.global;

import com.example.restapi.Member.controller.MemberController;
import com.example.restapi.Order.controller.OrderController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice(assignableTypes = {MemberController.class, OrderController.class})
public class MemberControllerAdvice {
    //회원가입 bean validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<List<ErrorDetail>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorDetail> list = ex.getFieldErrors()
                                   .stream()
                                   .map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                                   .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ApiResponse.failure("Validation failed", list, HttpStatus.BAD_REQUEST));
    }
}
