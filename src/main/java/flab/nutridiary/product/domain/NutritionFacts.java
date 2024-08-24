package flab.nutridiary.product.domain;

import flab.nutridiary.commom.generic.Nutrition;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@NoArgsConstructor
public class NutritionFacts {
    private static final String GRAM = "gram";

    private Nutrition totalNutrition;

    private BigDecimal productServingSize;

    private String productServingUnit;

    private BigDecimal productTotalWeightGram;

    @Builder
    public NutritionFacts(Nutrition totalNutrition, BigDecimal productServingSize, String productServingUnit, BigDecimal productTotalWeightGram) {
        this.totalNutrition = totalNutrition;
        this.productServingSize = productServingSize;
        this.productServingUnit = productServingUnit;
        this.productTotalWeightGram = productTotalWeightGram;
    }

    public Nutrition calculate(String servingUnit, BigDecimal quantity) {
        if (servingUnit.equals(GRAM)) {
            return totalNutrition.divide(productTotalWeightGram).multiply(quantity);
        }
        return totalNutrition.divide(productServingSize).multiply(quantity);
    }
}
