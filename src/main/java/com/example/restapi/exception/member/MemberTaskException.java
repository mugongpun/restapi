package com.example.restapi.exception.member;

public class MemberTaskException extends RuntimeException{

    private String message;
    private int status;

    public MemberTaskException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public static enum Exceptions {

        NOT_FOUND("NOT_FOUND", 404),
        DUPLICATE("DUPLICATE", 409),
        INVALID("INVALID", 400),
        BAD_CREDENTIALS("BAD CREDENTIALS",401); //멤버 인증과정에서 일어나는 에러

        private String message;
        private int status;

        Exceptions(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public MemberTaskException value() {
            return new MemberTaskException(message, status);
        }
    }
}
