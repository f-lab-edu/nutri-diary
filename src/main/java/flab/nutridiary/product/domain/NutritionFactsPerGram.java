package flab.nutridiary.product.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@ToString
public class NutritionFactsPerGram {
    private BigDecimal productCaloriesPerGram;

    private BigDecimal productCarbohydratePerGram;

    private BigDecimal productProteinPerGram;

    private BigDecimal productFatPerGram;

    @Builder
    private NutritionFactsPerGram(BigDecimal productCaloriesPerGram, BigDecimal productCarbohydratePerGram, BigDecimal productProteinPerGram, BigDecimal productFatPerGram) {
        this.productCaloriesPerGram = productCaloriesPerGram;
        this.productCarbohydratePerGram = productCarbohydratePerGram;
        this.productProteinPerGram = productProteinPerGram;
        this.productFatPerGram = productFatPerGram;
    }
}
