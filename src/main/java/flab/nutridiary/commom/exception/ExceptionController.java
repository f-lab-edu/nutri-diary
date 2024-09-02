package flab.nutridiary.commom.exception;

import flab.nutridiary.commom.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static flab.nutridiary.commom.exception.StatusConst.*;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> BusinessExceptionHandler(BusinessException e) {
        return ApiResponse.bizException(e.getStatusCode(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(SystemException.class)
    public ApiResponse<Void> SystemExceptionHandler(SystemException e) {
        log.info("SystemException : {}", e.getMessage());
        return ApiResponse.sysException();
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return ApiResponse.bizException(VALIDATION_CHECK_FAIL.getStatusCode(), VALIDATION_CHECK_FAIL.getMessage(), errors);
    }
}
