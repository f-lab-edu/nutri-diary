package flab.nutridiary.diary.dto;

import flab.nutridiary.diary.domain.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@ToString
public class NewDiaryRequest {
    @NotNull(message = "상품 ID를 입력해주세요.")
    private Long productId;

    private Long memberId = 1L;

    @NotNull(message = "식사 타입을 입력해주세요.")
    private MealType mealType;

    @NotNull(message = "섭취량을 입력해주세요.")
    @Min(value = 0, message = "섭취량은 0 이상이어야 합니다.")
    private BigDecimal quantity;

    @NotNull(message = "서빙 단위를 입력해주세요.")
    private String servingUnit;

    @NotNull(message = "섭취 날짜를 입력해주세요.")
    private LocalDate intakeDate;

    public NewDiaryRequest(Long productId, MealType mealType, BigDecimal quantity, String servingUnit, LocalDate intakeDate) {
        this.productId = productId;
        this.mealType = mealType;
        this.quantity = quantity;
        this.servingUnit = servingUnit;
        this.intakeDate = intakeDate;
    }
}
