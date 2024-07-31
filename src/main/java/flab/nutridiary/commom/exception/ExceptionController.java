package flab.nutridiary.commom.exception;

import flab.nutridiary.commom.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> BusinessExceptionHandler(BusinessException e) {
        return ApiResponse.failure(e.getStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SystemException.class)
    public ApiResponse<?> SystemExceptionHandler(SystemException e) {
        return ApiResponse.failure(e.getStatusCode(), e.getMessage());
    }
}
