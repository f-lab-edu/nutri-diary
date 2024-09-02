package flab.nutridiary.commom.generic.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.commom.generic.Nutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@RequiredArgsConstructor
@WritingConverter
public class NutritionToJsonConverter implements Converter<Nutrition, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convert(Nutrition nutrition) {
        try {
            return objectMapper.writeValueAsString(nutrition);
        } catch (JsonProcessingException e) {
            throw new SystemException("Nutrition 객체를 JSON 타입으로 변경에 실패했습니다.");
        }
    }
}