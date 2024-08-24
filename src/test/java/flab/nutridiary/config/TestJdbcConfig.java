package flab.nutridiary.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import flab.nutridiary.config.converter.JsonToNutritionFactsTestConverter;
import flab.nutridiary.config.converter.JsonToNutritionTestConverter;
import flab.nutridiary.config.converter.NutritionFactsToJsonTestConverter;
import flab.nutridiary.config.converter.NutritionToJsonTestConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestJdbcConfig {

    @Bean
    public JdbcCustomConversions jdbcCustomConversions(ObjectMapper objectMapper) {
        return new JdbcCustomConversions(Arrays.asList(
                new NutritionFactsToJsonTestConverter(objectMapper),
                new JsonToNutritionFactsTestConverter(objectMapper),
                new NutritionToJsonTestConverter(objectMapper),
                new JsonToNutritionTestConverter(objectMapper)
        ));
    }
}
