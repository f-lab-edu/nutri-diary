package flab.nutridiary.diary.domain;

import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerOneServing;

import java.math.BigDecimal;

public class DefaultNutritionCalculationStrategy implements NutritionCalculationStrategy {

    @Override
    public CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo) {
        NutritionFactsPerOneServing nutritionFactsPerOneServing = nutritionFacts.calculateNutritionFactsPerOneServingUnit();
        BigDecimal quantity = productIntakeInfo.getQuantity();
        return CalculatedNutrition.builder()
                .calories(nutritionFactsPerOneServing.getProductCaloriesPerOneServing().multiply(quantity))
                .carbohydrate(nutritionFactsPerOneServing.getProductCarbohydratePerOneServing().multiply(quantity))
                .protein(nutritionFactsPerOneServing.getProductProteinPerOneServing().multiply(quantity))
                .fat(nutritionFactsPerOneServing.getProductFatPerOneServing().multiply(quantity))
                .build();
    }
}
