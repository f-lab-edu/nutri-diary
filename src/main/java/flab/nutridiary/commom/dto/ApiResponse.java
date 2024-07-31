package flab.nutridiary.commom.dto;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final String success;
    private final int statusCode;
    private final T Data;
    private final String message;

    private ApiResponse(String success, Integer statusCode, T data, String message) {
        this.success = success;
        this.statusCode = statusCode;
        this.Data = data;
        this.message = message;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("ok", 200, data, null);
    }

    public static ApiResponse<?> failure(Integer statusCode, String message) {
        return new ApiResponse<>("fail", statusCode, null, message);
    }
}
