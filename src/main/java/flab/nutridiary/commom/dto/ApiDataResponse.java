package flab.nutridiary.commom.dto;

import lombok.Getter;

@Getter
public class ApiDataResponse<T> extends ApiResponse{

    private final T data;

    public ApiDataResponse(Boolean success, Integer statusCode, T data) {
        super(success, statusCode);
        this.data = data;
    }

    public static <T> ApiDataResponse<T> OK(T data) {
        return new ApiDataResponse<>(true, 200, data);
    }
}
