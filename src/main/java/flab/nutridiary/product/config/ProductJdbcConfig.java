package flab.nutridiary.product.config;

import flab.nutridiary.product.domain.converter.JsonToNutritionFactsConverter;
import flab.nutridiary.product.domain.converter.NutritionFactsToJsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import java.util.Arrays;

@Configuration
public class ProductJdbcConfig extends AbstractJdbcConfiguration {

    // JdbcCustomConversions 빈을 등록한다.
    @Bean
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                new NutritionFactsToJsonConverter(),
                new JsonToNutritionFactsConverter()
        ));
    }
}