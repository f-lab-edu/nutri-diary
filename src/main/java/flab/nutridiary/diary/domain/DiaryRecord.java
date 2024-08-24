package flab.nutridiary.diary.domain;

import flab.nutridiary.commom.generic.Nutrition;
import flab.nutridiary.product.domain.Product;
import lombok.*;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
public class DiaryRecord {
    private AggregateReference<Product, Long> productId;

    private MealType mealType;

    private BigDecimal quantity;

    private String servingUnit;

    private Nutrition calculatedNutrition;

    @Builder
    public DiaryRecord(Long productId, MealType mealType, BigDecimal quantity, String servingUnit, Nutrition calculatedNutrition) {
        this.productId = AggregateReference.to(productId);
        this.mealType = mealType;
        this.quantity = quantity;
        this.servingUnit = servingUnit;
        this.calculatedNutrition = calculatedNutrition;
    }

    public static DiaryRecord of(ProductIntakeInfo productIntakeInfo, NutritionCalculator nutritionCalculator) {
        return DiaryRecord.builder()
                .productId(productIntakeInfo.getProductId())
                .mealType(productIntakeInfo.getMealType())
                .quantity(productIntakeInfo.getQuantity())
                .servingUnit(productIntakeInfo.getServingUnit())
                .calculatedNutrition(nutritionCalculator.calculate(productIntakeInfo))
                .build();
    }
}
