package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerOneServing;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
@Slf4j
public class DefaultNutritionCalculationStrategy implements NutritionCalculationStrategy {

    @Override
    public CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo) {
        NutritionFactsPerOneServing nutritionFactsPerOneServing = nutritionFacts.calculateNutritionFactsPerOneServingUnit();
        BigDecimal quantity = productIntakeInfo.getQuantity();
        return CalculatedNutrition.builder()
                .calories(stripIfNecessary(
                        nutritionFactsPerOneServing.getProductCaloriesPerOneServing().multiply(quantity)))
                .carbohydrate(stripIfNecessary(
                        nutritionFactsPerOneServing.getProductCarbohydratePerOneServing().multiply(quantity)))
                .protein(stripIfNecessary(
                        nutritionFactsPerOneServing.getProductProteinPerOneServing().multiply(quantity)))
                .fat(stripIfNecessary(
                        nutritionFactsPerOneServing.getProductFatPerOneServing().multiply(quantity)))
                .build();
    }

    private BigDecimal stripIfNecessary(BigDecimal value) {
        BigDecimal strippedValue = value.stripTrailingZeros();
        return strippedValue.scale() <= 0 ? strippedValue.setScale(0) : strippedValue;
    }
}
