package com.example.restapi.controller;


public enum ResponseMessage {
    PRODUCT_ADD_SUCCESS("상품 등록에 성공하였습니다"),
    PRODUCT_DELETE_SUCCESS("상품 삭제에 성공하였습니다"),
    MEMBER_REGISTER_SUCCESS("멤버 등록에 성공하였습니다");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
