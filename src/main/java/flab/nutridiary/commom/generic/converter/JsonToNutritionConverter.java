package flab.nutridiary.commom.generic.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.commom.generic.Nutrition;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@RequiredArgsConstructor
@ReadingConverter
public class JsonToNutritionConverter implements Converter<String, Nutrition> {

    private final ObjectMapper objectMapper;

    @Override
    public Nutrition convert(String source) {
        try {
            return objectMapper.readValue(source, Nutrition.class);
        } catch (IOException e) {
            throw new SystemException("JSON 타입을 CalculatedNutrition 객체로 변경하는데 실패했습니다.");
        }
    }
}


