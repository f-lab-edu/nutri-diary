package flab.nutridiary.commom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.diary.domain.converter.CalculatedNutritionToJsonConverter;
import flab.nutridiary.diary.domain.converter.JsonToCalculatedNutritionConverter;
import flab.nutridiary.product.domain.converter.JsonToNutritionFactsConverter;
import flab.nutridiary.product.domain.converter.NutritionFactsToJsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {

    // JdbcCustomConversions 빈을 등록한다.
    @Bean
    public JdbcCustomConversions jdbcCustomConversions(ObjectMapper objectMapper) {
        return new JdbcCustomConversions(Arrays.asList(
                new NutritionFactsToJsonConverter(objectMapper),
                new JsonToNutritionFactsConverter(objectMapper),
                new CalculatedNutritionToJsonConverter(objectMapper),
                new JsonToCalculatedNutritionConverter(objectMapper)
        ));
    }
}
