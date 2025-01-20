package com.example.restapi.advice;

import com.example.restapi.exception.member.MemberTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TokenControllerAdvice {

    @ExceptionHandler(MemberTaskException.class)
    public ResponseEntity<Map<String, String>> handleMemberTaskException(MemberTaskException ex) {
        String msg = ex.getMessage();
        int status = ex.getStatus();

        return ResponseEntity.status(status)
                             .body(Map.of("error", msg));
    }

    //인증되지 않은 사용자의 접근시 발생 에러
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("message", e.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

}
