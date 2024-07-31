package flab.nutridiary.commom.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{
    private final int statusCode;

    public SystemException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
