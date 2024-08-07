package flab.nutridiary.commom.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException{
    private static final String MESSAGE = "서버에서 에러가 발생했습니다.";

    public SystemException() {
        super(MESSAGE);
    }

    public SystemException(String message) {
        super(message);
    }
}
