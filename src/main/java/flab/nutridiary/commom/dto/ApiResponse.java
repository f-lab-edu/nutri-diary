package flab.nutridiary.commom.dto;

import lombok.Getter;

import static flab.nutridiary.commom.exception.StatusConst.OK;
import static flab.nutridiary.commom.exception.StatusConst.SYSTEM_ERROR;

@Getter
public class ApiResponse<T> {
    private final int statusCode;
    private final String message;
    private final T Data;

    private ApiResponse(Integer statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.Data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(OK.getStatusCode(), OK.getMessage(), data);
    }

    public static ApiResponse<Void> bizException(Integer statusCode, String message) {
        return new ApiResponse<>(statusCode, message, null);
    }

    public static <T> ApiResponse<T> bizException(Integer statusCode, String message, T errors) {
        return new ApiResponse<>(statusCode, message, errors);
    }

    public static ApiResponse<Void> sysException() {
        return new ApiResponse<>(SYSTEM_ERROR.getStatusCode(), SYSTEM_ERROR.getMessage(), null);
    }
}
