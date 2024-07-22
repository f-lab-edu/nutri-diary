package flab.nutridiary.diary.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class CalculatedNutrition {
    private BigDecimal calories;
    private BigDecimal carbohydrate;
    private BigDecimal protein;
    private BigDecimal fat;

    @Builder
    public CalculatedNutrition(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }
}
