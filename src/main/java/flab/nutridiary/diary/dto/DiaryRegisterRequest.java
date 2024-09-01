package flab.nutridiary.diary.dto;

import flab.nutridiary.commom.validation.EnumValidator;
import flab.nutridiary.diary.domain.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
public class DiaryRegisterRequest {
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;

    private Long memberId = 1L;

    @EnumValidator(enumClass = MealType.class, message = "올바른 식사 타입을 입력해주세요.")
    private String mealType;

    @NotNull(message = "섭취량을 입력해주세요.")
    @Min(value = 0, message = "섭취량은 0 이상이어야 합니다.")
    private BigDecimal quantity;

    @NotNull(message = "서빙 단위를 입력해주세요.")
    private String clientChoiceServingUnit;

    @NotNull(message = "섭취 날짜를 입력해주세요.")
    private LocalDate intakeDate;

    public DiaryRegisterRequest(Long productId, String mealType, BigDecimal quantity, String clientChoiceServingUnit, LocalDate intakeDate) {
        this.productId = productId;
        this.mealType = mealType;
        this.quantity = quantity;
        this.clientChoiceServingUnit = clientChoiceServingUnit;
        this.intakeDate = intakeDate;
    }
}
