package flab.nutridiary.commom.exception;

import flab.nutridiary.commom.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}
