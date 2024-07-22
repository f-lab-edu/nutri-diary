package flab.nutridiary.diary.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.diary.domain.CalculatedNutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@RequiredArgsConstructor
@WritingConverter
public class CalculatedNutritionToJsonConverter implements Converter<CalculatedNutrition, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convert(CalculatedNutrition calculatedNutrition) {
        try {
            return objectMapper.writeValueAsString(calculatedNutrition);
        } catch (JsonProcessingException e) {
            throw new SystemException("CalculatedNutrition 객체를 JSON 타입으로 변경에 실패했습니다.");
        }
    }
}