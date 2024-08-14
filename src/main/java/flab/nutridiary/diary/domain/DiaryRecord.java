package flab.nutridiary.diary.domain;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@Getter
@NoArgsConstructor
public class DiaryRecord {
    private Long productId;

    private MealType mealType;

    private BigDecimal quantity;

    private String servingUnit;

    private CalculatedNutrition calculatedNutrition;

    @Builder
    public DiaryRecord(Long productId, MealType mealType, BigDecimal quantity, String servingUnit, CalculatedNutrition calculatedNutrition) {
        this.productId = productId;
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
