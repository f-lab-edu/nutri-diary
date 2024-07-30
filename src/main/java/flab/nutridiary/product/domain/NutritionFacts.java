package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

import static flab.nutridiary.product.constant.DecimalConstant.ROUNDING_MODE;
import static flab.nutridiary.product.constant.DecimalConstant.SCALE;
import static java.math.BigDecimal.valueOf;

@Getter
@ToString
@NoArgsConstructor
public class NutritionFacts {
    private BigDecimal productCalories;

    private BigDecimal productCarbohydrate;

    private BigDecimal productProtein;

    private BigDecimal productFat;

    private String productServingUnit;

    private int productServingWeightGram;

    @Builder
    public NutritionFacts(BigDecimal productCalories, BigDecimal productCarbohydrate, BigDecimal productProtein, BigDecimal productFat, String productServingUnit, int productServingWeightGram) {
        this.productCalories = productCalories;
        this.productCarbohydrate = productCarbohydrate;
        this.productProtein = productProtein;
        this.productFat = productFat;
        this.productServingUnit = productServingUnit;
        this.productServingWeightGram = productServingWeightGram;
    }

    public NutritionFactsPerGram calculateNutritionFactsPerGram() {
        return NutritionFactsPerGram.builder()
                .productCaloriesPerGram(productCalories.divide(valueOf(productServingWeightGram), SCALE, ROUNDING_MODE))
                .productCarbohydratePerGram(productCarbohydrate.divide(valueOf(productServingWeightGram), SCALE, ROUNDING_MODE))
                .productProteinPerGram(productProtein.divide(valueOf(productServingWeightGram), SCALE, ROUNDING_MODE))
                .productFatPerGram(productFat.divide(valueOf(productServingWeightGram), SCALE, ROUNDING_MODE))
                .build();

    }
}
