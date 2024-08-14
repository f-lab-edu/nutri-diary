package flab.nutridiary.product.domain;

import lombok.*;

import java.math.BigDecimal;

import static flab.nutridiary.product.constant.DecimalConstant.ROUNDING_MODE;
import static flab.nutridiary.product.constant.DecimalConstant.SCALE;

@ToString
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
                .productCaloriesPerGram(stripIfNecessary(
                        productTotalCalories.divide(productTotalWeightGram, SCALE, ROUNDING_MODE)))
                .productCarbohydratePerGram(stripIfNecessary(
                        productTotalCarbohydrate.divide(productTotalWeightGram, SCALE, ROUNDING_MODE)))
                .productProteinPerGram(stripIfNecessary(
                        productTotalProtein.divide(productTotalWeightGram, SCALE, ROUNDING_MODE)))
                .productFatPerGram(stripIfNecessary(
                        productTotalFat.divide(productTotalWeightGram, SCALE, ROUNDING_MODE)))
                .build();
    }

    public NutritionFactsPerOneServing calculateNutritionFactsPerOneServingUnit() {
        return NutritionFactsPerOneServing.builder()
                .productCaloriesPerOneServing(stripIfNecessary(
                        productTotalCalories.divide(productServingSize, SCALE, ROUNDING_MODE)))
                .productCarbohydratePerOneServing(stripIfNecessary(
                        productTotalCarbohydrate.divide(productServingSize, SCALE, ROUNDING_MODE)))
                .productProteinPerOneServing(stripIfNecessary(
                        productTotalProtein.divide(productServingSize, SCALE, ROUNDING_MODE)))
                .productFatPerOneServing(stripIfNecessary(
                        productTotalFat.divide(productServingSize, SCALE, ROUNDING_MODE)))
                .build();
    }

    private BigDecimal stripIfNecessary(BigDecimal value) {
        BigDecimal strippedValue = value.stripTrailingZeros();
        return strippedValue.scale() <= 0 ? strippedValue.setScale(0) : strippedValue;
    }
}
