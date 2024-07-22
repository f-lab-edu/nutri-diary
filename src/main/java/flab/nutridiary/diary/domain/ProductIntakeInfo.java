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
    private final String ServingUnit;

    @Builder
    public ProductIntakeInfo(Long productId, MealType mealType, String servingUnit, BigDecimal quantity, String ServingUnit) {
        this.productId = productId;
        this.mealType = mealType;
        this.servingUnit = servingUnit;
        this.quantity = quantity;
        this.ServingUnit = ServingUnit;
    }
}
