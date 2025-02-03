package com.example.restapi.controller;


public enum ResponseMessage {
    PRODUCT_ADD_SUCCESS("상품 등록에 성공하였습니다"),
    PRODUCT_DELETE_SUCCESS("상품 삭제에 성공하였습니다"),
    MEMBER_REGISTER_SUCCESS("회원 가입에 성공하였습니다"),
    MEMBER_UPDATE_SUCCESS("회원 정보 수정이 완료되었습니다"),
    MEMBER_DELETE_SUCCESS("회원 탈퇴가 완료되었습니다");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
