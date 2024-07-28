package flab.nutridiary.product.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.product.domain.NutritionFacts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NutritionFactsToJsonConverterTest {

    private final NutritionFactsToJsonConverter converter = new NutritionFactsToJsonConverter();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("NutritionFacts 객체를 JSON 문자열로 변환한다.")
    @Test
    void convert() throws Exception {
        // given
        NutritionFacts nutritionFacts = new NutritionFacts(
                new BigDecimal("100"),
                new BigDecimal("20"),
                new BigDecimal("10"),
                new BigDecimal("5"),
                "grams",
                200
        );

        // when
        String nutritionFactsJson = converter.convert(nutritionFacts);

        // then
        NutritionFacts result = objectMapper.readValue(nutritionFactsJson, NutritionFacts.class);
        assertEquals(nutritionFacts.getProductCalories(), result.getProductCalories());
        assertEquals(nutritionFacts.getProductCarbohydrate(), result.getProductCarbohydrate());
        assertEquals(nutritionFacts.getProductProtein(), result.getProductProtein());
        assertEquals(nutritionFacts.getProductFat(), result.getProductFat());
        assertEquals(nutritionFacts.getProductServingUnit(), result.getProductServingUnit());
        assertEquals(nutritionFacts.getProductServingWeightGram(), result.getProductServingWeightGram());
    }
}
