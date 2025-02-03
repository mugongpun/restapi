package com.example.restapi.global;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private HttpStatus code;  // HTTP 상태 코드 (선택 사항)

    // 기본 생성자 (기본값 설정)
    public ApiResponse() {
    }

    // 성공 응답을 위한 생성자
    public ApiResponse(String message, T data) {
        this.success = true;
        this.message = message;
        this.data = data;
        this.code = HttpStatus.OK; // 기본적으로 성공 응답 (HTTP 200)
    }

    // 실패 응답을 위한 생성자
    public ApiResponse(String message, HttpStatus code) {
        this.success = false;
        this.message = message;
        this.code = code;
    }

    public ApiResponse(String message, HttpStatus code, T data) {
        this.success = false;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public ApiResponse(T data) {
        this("Success", data);
    }


    // 성공 여부, 메시지, 데이터를 설정하는 메서드들
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> success(ResponseMessage message, T data) {
        return new ApiResponse<>(message.getMessage(), data);
    }

    public static ApiResponse<Void> failure(String message, HttpStatus code) {
        return new ApiResponse<>(message, code);
    }

    public static <T> ApiResponse<T> failure(String message, T data, HttpStatus code) {
        return new ApiResponse<>(message, code, data);
    }
}
