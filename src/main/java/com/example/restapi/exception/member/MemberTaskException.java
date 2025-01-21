package com.example.restapi.exception.member;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MemberTaskException extends RuntimeException{

    private String message;
    private HttpStatus status;

    public MemberTaskException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public static enum Exceptions {

        NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND),
        DUPLICATE("DUPLICATE", HttpStatus.CONFLICT),
        INVALID("INVALID", HttpStatus.BAD_REQUEST),
        BAD_CREDENTIALS("BAD CREDENTIALS",HttpStatus.UNAUTHORIZED); //멤버 인증과정에서 일어나는 에러

        private String message;
        private HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public MemberTaskException createException() {
            return new MemberTaskException(message, status);
        }
    }
}
