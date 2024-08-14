package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerGram;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

@Slf4j
public class GramBasedNutritionCalculationStrategy implements NutritionCalculationStrategy {

    @Override
    public CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo) {
        NutritionFactsPerGram nutritionFactsPerGram = nutritionFacts.calculateNutritionFactsPerGram();
        BigDecimal quantity = productIntakeInfo.getQuantity();
        return CalculatedNutrition.builder()
                .calories(stripIfNecessary(
                        nutritionFactsPerGram.getProductCaloriesPerGram().multiply(quantity)))
                .carbohydrate(stripIfNecessary(
                        nutritionFactsPerGram.getProductCarbohydratePerGram().multiply(quantity)))
                .protein(stripIfNecessary(
                        nutritionFactsPerGram.getProductProteinPerGram().multiply(quantity)))
                .fat(stripIfNecessary(
                        nutritionFactsPerGram.getProductFatPerGram().multiply(quantity)))
                .build();
    }

    private BigDecimal stripIfNecessary(BigDecimal value) {
        BigDecimal strippedValue = value.stripTrailingZeros();
        return strippedValue.scale() <= 0 ? strippedValue.setScale(0) : strippedValue;
    }
}
