package flab.nutridiary.commom.dto;

import lombok.Getter;

@Getter
public class ApiExceptionResponse extends ApiResponse{

    private final String message;

    private ApiExceptionResponse(Boolean success, Integer statusCode, String message) {
        super(success, statusCode);
        this.message = message;
    }

    public static <T> ApiExceptionResponse EX(Integer statusCode, String message) {
        return new ApiExceptionResponse(true, statusCode, message);
    }

    public static <T> ApiExceptionResponse ERROR(Integer statusCode, String message) {
        return new ApiExceptionResponse(false, statusCode, message);
    }
}
