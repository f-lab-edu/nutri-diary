package flab.nutridiary.product.domain;

import lombok.*;

import java.math.BigDecimal;

import static flab.nutridiary.product.constant.DecimalConstant.ROUNDING_MODE;
import static flab.nutridiary.product.constant.DecimalConstant.SCALE;

@Getter
@NoArgsConstructor
public class NutritionFacts {
    private BigDecimal productTotalCalories;

    private BigDecimal productTotalCarbohydrate;

    private BigDecimal productTotalProtein;

    private BigDecimal productTotalFat;

    private BigDecimal productServingSize;

    private String productServingUnit;

    private BigDecimal productTotalWeightGram;

    @Builder
    public NutritionFacts(BigDecimal productTotalCalories, BigDecimal productTotalCarbohydrate, BigDecimal productTotalProtein, BigDecimal productTotalFat, BigDecimal productServingSize, String productServingUnit, BigDecimal productTotalWeightGram) {
        this.productTotalCalories = productTotalCalories;
        this.productTotalCarbohydrate = productTotalCarbohydrate;
        this.productTotalProtein = productTotalProtein;
        this.productTotalFat = productTotalFat;
        this.productServingSize = productServingSize;
        this.productServingUnit = productServingUnit;
        this.productTotalWeightGram = productTotalWeightGram;
    }

    public NutritionFactsPerGram calculateNutritionFactsPerGram() {
        return NutritionFactsPerGram.builder()
                .productCaloriesPerGram(productTotalCalories.divide(productTotalWeightGram, SCALE, ROUNDING_MODE))
                .productCarbohydratePerGram(productTotalCarbohydrate.divide(productTotalWeightGram, SCALE, ROUNDING_MODE))
                .productProteinPerGram(productTotalProtein.divide(productTotalWeightGram, SCALE, ROUNDING_MODE))
                .productFatPerGram(productTotalFat.divide(productTotalWeightGram, SCALE, ROUNDING_MODE))
                .build();
    }
}
