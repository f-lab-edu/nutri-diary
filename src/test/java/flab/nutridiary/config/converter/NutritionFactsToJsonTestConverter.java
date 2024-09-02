package flab.nutridiary.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.product.domain.NutritionFacts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@RequiredArgsConstructor
@WritingConverter
public class NutritionFactsToJsonTestConverter implements Converter<NutritionFacts, byte[]> {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] convert(NutritionFacts nutritionFacts) {
        try {
            return objectMapper.writeValueAsBytes(nutritionFacts);
        } catch (JsonProcessingException e) {
            throw new SystemException("NutritionFacts 객체를 JSON 타입으로 변경에 실패했습니다.");
        }
    }
}