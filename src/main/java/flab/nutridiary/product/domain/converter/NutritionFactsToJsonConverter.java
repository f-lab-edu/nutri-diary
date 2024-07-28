package flab.nutridiary.product.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.product.domain.NutritionFacts;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class NutritionFactsToJsonConverter implements Converter<NutritionFacts, String> {

    private final ObjectMapper objectMapper;

    public NutritionFactsToJsonConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String convert(NutritionFacts nutritionFacts) {
        try {
            return objectMapper.writeValueAsString(nutritionFacts);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("NutritionFacts 객체를 JSON 타입으로 변경에 실패했습니다.", e);
        }
    }
}