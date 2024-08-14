package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.product.domain.NutritionFacts;
import flab.nutridiary.product.domain.NutritionFactsPerGram;

import java.math.BigDecimal;

public class GramBasedNutritionCalculationStrategy implements NutritionCalculationStrategy {

    @Override
    public CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo) {
        NutritionFactsPerGram nutritionFactsPerGram = nutritionFacts.calculateNutritionFactsPerGram();
        BigDecimal quantity = productIntakeInfo.getQuantity();
        return CalculatedNutrition.builder()
                .calories(nutritionFactsPerGram.getProductCaloriesPerGram().multiply(quantity))
                .carbohydrate(nutritionFactsPerGram.getProductCarbohydratePerGram().multiply(quantity))
                .protein(nutritionFactsPerGram.getProductProteinPerGram().multiply(quantity))
                .fat(nutritionFactsPerGram.getProductFatPerGram().multiply(quantity))
                .build();
    }
}
