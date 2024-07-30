package flab.nutridiary.product.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class NutritionFactsTest {

    @DisplayName("NutritionFacts 객체로부터 단위 그램당 영양정보를 얻는다.")
    @Test
    void calculateNutritionFactsPerGram() throws Exception {
        // given
        NutritionFacts nutritionFacts = NutritionFacts.builder()
                .productCalories(BigDecimal.valueOf(100))
                .productCarbohydrate(BigDecimal.valueOf(10))
                .productProtein(BigDecimal.valueOf(20))
                .productFat(BigDecimal.valueOf(30))
                .productServingUnit("컵")
                .productServingWeightGram(80)
                .build();

        // when
        NutritionFactsPerGram result = nutritionFacts.calculateNutritionFactsPerGram();

        // then
        assertThat(result.getProductCaloriesPerGram()).isEqualTo(BigDecimal.valueOf(1.25));
        assertThat(result.getProductCarbohydratePerGram()).isEqualTo(BigDecimal.valueOf(0.13));
        assertThat(result.getProductProteinPerGram()).isEqualTo(BigDecimal.valueOf(0.25));
        assertThat(result.getProductFatPerGram()).isEqualTo(BigDecimal.valueOf(0.38));

    }

}