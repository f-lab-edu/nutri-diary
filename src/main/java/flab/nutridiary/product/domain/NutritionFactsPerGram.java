package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@ToString
public class NutritionFactsPerGram {
    @Id
    private String id;

    private BigDecimal calories;

    private BigDecimal carbohydrate;

    private BigDecimal protein;

    private BigDecimal fat;

    @Builder
    public NutritionFactsPerGram(BigDecimal calories, BigDecimal carbohydrate, BigDecimal protein, BigDecimal fat) {
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }
}
