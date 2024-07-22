package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@ToString
@Table
public class NutritionFacts {
    private BigDecimal calories;

    private BigDecimal carbohydrate;

    private BigDecimal protein;

    private BigDecimal fat;

    private String servingUnit;

    private int servingWeightGram;

    @Builder
    private NutritionFacts(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat, String servingUnit, int servingWeightGram) {
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.servingUnit = servingUnit;
        this.servingWeightGram = servingWeightGram;
    }
}
