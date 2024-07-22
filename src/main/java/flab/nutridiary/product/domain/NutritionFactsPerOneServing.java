package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NutritionFactsPerOneServing {
    private BigDecimal productCaloriesPerOneServing;

    private BigDecimal productCarbohydratePerOneServing;

    private BigDecimal productProteinPerOneServing;

    private BigDecimal productFatPerOneServing;

    @Builder
    private NutritionFactsPerOneServing(BigDecimal productCaloriesPerOneServing, BigDecimal productCarbohydratePerOneServing, BigDecimal productProteinPerOneServing, BigDecimal productFatPerOneServing) {
        this.productCaloriesPerOneServing = productCaloriesPerOneServing;
        this.productCarbohydratePerOneServing = productCarbohydratePerOneServing;
        this.productProteinPerOneServing = productProteinPerOneServing;
        this.productFatPerOneServing = productFatPerOneServing;
    }
}
