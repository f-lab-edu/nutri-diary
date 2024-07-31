package flab.nutridiary.commom.exception;

import lombok.Getter;

@Getter
public class BusinessException extends Exception{
    private final int statusCode;

    public BusinessException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
