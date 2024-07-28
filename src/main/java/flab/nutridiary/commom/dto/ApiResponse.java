package flab.nutridiary.commom.dto;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final Boolean success;
    private final Integer statusCode;

    public ApiResponse(Boolean success, Integer statusCode) {
        this.success = success;
        this.statusCode = statusCode;
    }
}
