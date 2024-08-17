package flab.nutridiary.commom.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusConst {
    OK(2001, "OK"),
    SYSTEM_ERROR(5001, "서버에서 에러가 발생했습니다."),
    INVALID_INPUT_VALUE(4001, "입력값이 올바르지 않습니다."),
    DUPLICATED_PRODUCT_NAME(4002, "이미 등록된 식품입니다."),
    VALIDATION_CHECK_FAIL(4004, "유효성 검사에 실패했습니다.");


    private final int statusCode;
    private final String message;
}
