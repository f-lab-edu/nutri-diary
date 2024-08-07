package flab.nutridiary.product.domain.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.commom.exception.SystemException;
import flab.nutridiary.product.domain.NutritionFacts;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.io.IOException;

@RequiredArgsConstructor
@ReadingConverter
public class JsonToNutritionFactsConverter implements Converter<byte[], NutritionFacts> {

    private final ObjectMapper objectMapper;

    @Override
    public NutritionFacts convert(byte[] source) {
        try {
            String jsonSource = objectMapper.readValue(source, String.class);
            return objectMapper.readValue(jsonSource, NutritionFacts.class);
        } catch (IOException e) {
            throw new SystemException("JSON 타입을 NutritionFacts 객체로 변경하는데 실패했습니다.");
        }
    }
}

