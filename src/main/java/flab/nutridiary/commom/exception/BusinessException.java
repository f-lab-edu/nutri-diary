package flab.nutridiary.commom.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int statusCode;

    public BusinessException(StatusConst statusConst) {
        super(statusConst.getMessage());
        this.statusCode = statusConst.getStatusCode();
    }
}
