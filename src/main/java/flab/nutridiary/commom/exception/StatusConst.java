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
    INVALID_PRODUCT_ID(4003, "존재하지 않는 식품입니다."),
    DIARY_NOT_FOUND(4004, "해당 다이어리를 찾을 수 없습니다."),
    DUPLICATED_DIARY(4005, "이미 등록된 다이어리입니다.");

    private final int statusCode;
    private final String message;
}
