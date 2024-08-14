package flab.nutridiary.diary.domain.calculator;

import flab.nutridiary.diary.domain.CalculatedNutrition;
import flab.nutridiary.diary.domain.ProductIntakeInfo;
import flab.nutridiary.product.domain.NutritionFacts;

public interface NutritionCalculationStrategy {
    CalculatedNutrition calculate(NutritionFacts nutritionFacts, ProductIntakeInfo productIntakeInfo);
}
