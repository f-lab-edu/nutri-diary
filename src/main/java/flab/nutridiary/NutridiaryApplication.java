package flab.nutridiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EnableJdbcRepositories(excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "flab.nutridiary.search.*"))
@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
public class NutridiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(NutridiaryApplication.class, args);
	}

}
