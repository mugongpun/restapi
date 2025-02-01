package com.example.restapi.advice;

import com.example.restapi.controller.ApiResponse;
import com.example.restapi.exception.MemberTaskException;
import com.example.restapi.exception.TokenTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<ApiResponse<Void>> handleMemberTaskException(MemberTaskException ex) {
        String msg = ex.getMessage();
        HttpStatus status = ex.getStatus();

        return ResponseEntity.status(status)
                             .body(ApiResponse.failure(msg, status));
    }

    //인증되지 않은 사용자의 접근시 발생 에러
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                             .body(ApiResponse.failure(e.getMessage(), HttpStatus.FORBIDDEN));
    }

    @ExceptionHandler(TokenTaskException.class)
    public ResponseEntity<ApiResponse<Void>> handleTokenTaskException(TokenTaskException e) {
        return ResponseEntity.status(e.getStatus())
                             .body(ApiResponse.failure(e.getMessage(), e.getStatus()));
    }
}
