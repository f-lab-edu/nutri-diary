package flab.nutridiary.product.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.product.domain.NutritionFacts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonToNutritionFactsConverterTest {

    private final JsonToNutritionFactsConverter converter = new JsonToNutritionFactsConverter();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("JSON 문자열을 NutritionFacts 객체로 변환한다.")
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
        String nutritionFactsJson = objectMapper.writeValueAsString(nutritionFacts);

        // when
        NutritionFacts result = converter.convert(nutritionFactsJson);

        // then
        assertEquals(nutritionFacts.getProductCalories(), result.getProductCalories());
        assertEquals(nutritionFacts.getProductCarbohydrate(), result.getProductCarbohydrate());
        assertEquals(nutritionFacts.getProductProtein(), result.getProductProtein());
        assertEquals(nutritionFacts.getProductFat(), result.getProductFat());
    }
}