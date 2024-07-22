package flab.nutridiary.diary.domain;

import flab.nutridiary.product.domain.NutritionFacts;

public interface NutritionCalculationStrategy {
    CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo);
}
