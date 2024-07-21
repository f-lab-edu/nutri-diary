package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@ToString
public class NutritionFacts {
    @Id
    private String id;

    private BigDecimal calories;

    private BigDecimal carbohydrate;

    private BigDecimal protein;

    private BigDecimal fat;

    private String servingUnit;

    private int servingWeightGram;

    @Builder
    public NutritionFacts(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat, String servingUnit, int servingWeightGram) {
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.servingUnit = servingUnit;
        this.servingWeightGram = servingWeightGram;
    }
}
