package flab.nutridiary.diary.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ProductIntakeInfo {
    private final Long productId;
    private final MealType mealType;
    private final String clientChoiceServingUnitDescription;
    private final BigDecimal quantity;

    @Builder
    public ProductIntakeInfo(Long productId, String mealType, String clientChoiceServingUnitDescription, BigDecimal quantity) {
        this.productId = productId;
        this.mealType = MealType.valueOf(mealType);
        this.clientChoiceServingUnitDescription = clientChoiceServingUnitDescription;
        this.quantity = quantity;
    }
}
