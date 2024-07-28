package flab.nutridiary.commom.exception;

import flab.nutridiary.commom.dto.ApiExceptionResponse;
import flab.nutridiary.commom.dto.ApiResponse;
import flab.nutridiary.product.service.ProductDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductDuplicatedException.class)
    public ApiResponse productDuplicatedExceptionHandler(ProductDuplicatedException e) {
        return ApiExceptionResponse.EX(e.getStatusCode(), e.getMessage());
    }
}
