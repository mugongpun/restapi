package com.example.restapi.advice;

import com.example.restapi.exception.member.MemberTaskException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

}
