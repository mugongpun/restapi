package com.example.restapi.global.exception;

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

        NOT_FOUND("존재하는 회원이 아닙니다", HttpStatus.NOT_FOUND),
        INVALID("INVALID", HttpStatus.BAD_REQUEST),
        BAD_CREDENTIALS("BAD CREDENTIALS",HttpStatus.UNAUTHORIZED),
        DUPLICATE_VALUE("이미 존재하는 회원입니다", HttpStatus.CONFLICT),
        USERNAME_MISMATCH("사용자 아이디와 토큰의 아이디가 다릅니다", HttpStatus.UNAUTHORIZED);

        private String message;
        private HttpStatus status;

        Exceptions(String message, HttpStatus status) {
            this.message = message;
            this.status = status;
        }

        public MemberTaskException get() {
            return new MemberTaskException(message, status);
        }
    }
}
