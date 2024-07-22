package flab.nutridiary.diary.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NutritionCalculationStrategyFactory {
    private static final String GRAM = "gram";

    public NutritionCalculationStrategy getStrategy(String servingUnit) {
        if (GRAM.equals(servingUnit)) {
            return new GramBasedNutritionCalculationStrategy();
        } else {
            return new DefaultNutritionCalculationStrategy();
        }
    }
}
