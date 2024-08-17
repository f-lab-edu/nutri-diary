package flab.nutridiary.diary.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductIntakeInfo {
    private final Long productId;
    private final MealType mealType;
    private final String servingUnit;
    private final BigDecimal quantity;

    @Builder
    public ProductIntakeInfo(Long productId, String mealType, String servingUnit, BigDecimal quantity) {
        this.productId = productId;
        this.mealType = MealType.valueOf(mealType);
        this.servingUnit = servingUnit;
        this.quantity = quantity;
    }
}
