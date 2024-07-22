package flab.nutridiary.diary.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.diary.domain.CalculatedNutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@RequiredArgsConstructor
@ReadingConverter
public class JsonToCalculatedNutritionConverter implements Converter<String, CalculatedNutrition> {

    private final ObjectMapper objectMapper;

    @Override
    public CalculatedNutrition convert(String source) {
        try {
            return objectMapper.readValue(source, CalculatedNutrition.class);
        } catch (IOException e) {
            throw new SystemException("JSON 타입을 CalculatedNutrition 객체로 변경하는데 실패했습니다.");
        }
    }
}

